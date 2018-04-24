package com.ohadr.google_authenticator.authentication.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.ohadr.google_authenticator.TOTPCodeUtils;
import com.ohadr.google_authenticator.authentication.CustomWebAuthenticationDetails;
import com.ohadr.google_authenticator.repository.AuthenticationAccountRepository;
import com.ohadr.google_authenticator.repository.InMemoryAuthenticationUserImpl;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	private static Logger log = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
//	private UserRepository userRepository;
	private AuthenticationAccountRepository userRepository;

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException
	{
		CustomWebAuthenticationDetails authenticationDetails = (CustomWebAuthenticationDetails)auth.getDetails();
		String verificationCode = authenticationDetails.getVerificationCode();
		log.info("verification code: " + verificationCode);
		
		UserDetails user = userRepository.loadUserByUsername(auth.getName());
		if ((user == null)) {
			throw new BadCredentialsException("Invalid username or password");	//from security manner, do not return "usernameNotFound"
		}
		
		InMemoryAuthenticationUserImpl userImpl = (InMemoryAuthenticationUserImpl)user;
//for now, always use MFA:
//		if (userImpl.isUsing2FA()) {
			String mfaSecretKey = userImpl.getMfaSecretKey();	
			String code = TOTPCodeUtils.getTOTPCode( mfaSecretKey );
//			Totp totp = new Totp(user.getMfaSecretKey());
			if( ! verificationCode.equals(code) ) {
//			if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
				throw new BadCredentialsException("Invalid verfication code");
			}
//		}

		Authentication result = super.authenticate(auth);
		return new UsernamePasswordAuthenticationToken(
				user, result.getCredentials(), result.getAuthorities());
	}

	private boolean isValidLong(String code) {
		try {
			Long.parseLong(code);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}