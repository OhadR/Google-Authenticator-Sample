package com.ohadr.google_authenticator.web;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.ohadr.google_authenticator.TOTPCodeUtils;
import com.ohadr.google_authenticator.repository.AuthenticationAccountRepository;
import com.ohadr.google_authenticator.repository.InMemoryAuthenticationUserImpl;

@Controller
public class UserActions {

	private static Logger log = Logger.getLogger(UserActions.class);
	public static final String ACCOUNT_CREATION_HAS_FAILED_PASSWORDS_DO_NOT_MATCH = 
			"Account creation has failed. These passwords don't match";
	public static final String USER_ALREADY_EXIST = "USER_ALREADY_EXIST";
	private static final String LOGIN_FORMS_DIR = "login";
	public static final String EMAIL_PARAM_NAME = "email";
	private static final String CONFIRM_PASSWORD_PARAM_NAME = "confirm_password";

	@Autowired
	private AuthenticationAccountRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * we get the params from the HTML-form. 
	 * this method is called by AJAX from "create-account" form submission
	 * @param email
	 * @param password
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/createAccount")
	final protected View createAccount(
			@RequestParam( EMAIL_PARAM_NAME ) String email,
			@RequestParam("password") String password,
			@RequestParam( CONFIRM_PASSWORD_PARAM_NAME ) String retypedPassword,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		RedirectView rv = new RedirectView();

//		request.setAttribute("email", email);
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(EMAIL_PARAM_NAME,  email);		

		
		createAccount(email, password, retypedPassword);
		
		//adding attributes to the redirect return value:
		rv.setAttributesMap(attributes);
		rv.setUrl(LOGIN_FORMS_DIR +"/" + "accountCreatedSuccess.jsp");
		return rv;


	}

	public void createAccount(
			String email,
			String password,
			String retypedPassword) throws Exception
	{
		validateRetypedPassword(password, retypedPassword);

		//encoding the password:
		String encodedPassword = encodeString(email, password);

		internalCreateAccount(email, encodedPassword);
	}
	
	private void validateRetypedPassword(String password, String retypedPassword)
	{
		if(!password.equals(retypedPassword))
		{
			throw new IllegalArgumentException(ACCOUNT_CREATION_HAS_FAILED_PASSWORDS_DO_NOT_MATCH);
		}
	}
	
	private String encodeString(String salt, String rawPass) 
	{
		//the 'username' should be the salt (rather than null), but we use custom authentication provider, 
		//so it is problematic to send the salt... (during retrival it does not work)
		String encodedPassword = passwordEncoder.encodePassword(rawPass, null);	
		return encodedPassword;
	}


	private void internalCreateAccount(
			String email,
			String encodedPassword 
			) throws Exception 
	{
		email = email.toLowerCase();		// issue #23 : username is case-sensitive (https://github.com/OhadR/oAuth2-sample/issues/23)
		log.info("createAccount() for user " + email);

		try
		{
			User oauthUser = null;
			try
			{
				oauthUser = (User) repository.loadUserByUsername( email );
			}
			catch(UsernameNotFoundException unfe)
			{
				//basically do nothing - we expect user not to be found.
			}
			
			//if user exist, but not activated - we allow re-registration:
			if(oauthUser != null)
			{
				//error - user already exists and active
				log.error( "cannot create account - user " + email + " already exist." );
				throw new Exception( USER_ALREADY_EXIST );
			}

			Collection<? extends GrantedAuthority> authorities = setAuthorities();		//set authorities
			UserDetails user = new InMemoryAuthenticationUserImpl(
					email, encodedPassword, 
					false,									//start as de-activated
					5, //MaxPasswordEntryAttempts
					null,					//set by the repo-impl
					TOTPCodeUtils.getRandomSecretKey(),
					authorities);			

			repository.createUser(user);
		}
		//we should not get to these exceptions since we check earlier if account already exist (so repo's do not 
		// have to check it)
		catch(DataIntegrityViolationException e)
		{
			//get the cause-exception, since it has a better message:
			Throwable root = e.getRootCause();
			String msg = root.getMessage();
			Assert.isTrue(msg.contains("Duplicate entry"));
			
			log.error( msg );
			throw new Exception( USER_ALREADY_EXIST );
		}
	}

	private Collection<? extends GrantedAuthority> setAuthorities() 
	{
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_USER");
		set.add(auth);
		return set;		
	}


}
