package com.ohadr.google_authenticator.repository;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class InMemAuthenticationAccountRepositoryImpl extends AbstractAuthenticationAccountRepository
{
	private static Logger log = Logger.getLogger(InMemAuthenticationAccountRepositoryImpl.class);


	private Map<String, InMemoryAuthenticationUserImpl> dataStore = new HashMap<>();
	
	@Override
	public void createUser(UserDetails user)
	{
		InMemoryAuthenticationUserImpl userImpl = (InMemoryAuthenticationUserImpl)user;
		dataStore.put(userImpl.getUsername(), userImpl);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserDetails userFromDB = dataStore.get(username);
		if(userFromDB == null)
		{
			log.info("no record was found for email=" + username);
			throw new UsernameNotFoundException( username );
		}

		return userFromDB;
	}

	@Override
	public void deleteUser(String username)
	{
		UserDetails userFromDB = dataStore.remove(username);
		if (userFromDB == null)
		{
			throw new NoSuchElementException("No user with email: " + username);
		}
	}

	@Override
	public void changePassword(String username, String newEncodedPassword) 
	{
		throw new UnsupportedOperationException();
	}

    

	@Override
	public void setPassword(String email, String newPassword) 
	{
		changePassword(email, newPassword);
	}

	/******************************************************************/	
	@Override
	public void setEnabled(String email) 
	{
		setEnabledFlag(email, true);
	}

	@Override
	public void setDisabled(String email) 
	{
		setEnabledFlag(email, false);
	}

	@Override
	protected void setEnabledFlag(String email, boolean flag) 
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void updateUser(UserDetails user)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username)
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setAuthority(String username, String authority)
	{
		throw new UnsupportedOperationException();
//		int count = jdbcTemplate.update( DEFAULT_UPDATE_AUTHORITY_STATEMENT, authority, username);
//		if ( count != 1 )
//		{
//			throw new NoSuchElementException("No user with email: " + username);
//		}
	}


}