package com.ohadr.google_authenticator.repository;


import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UserDetails;


public abstract class AbstractAuthenticationAccountRepository implements AuthenticationAccountRepository
{
	protected abstract void setEnabledFlag(String email, boolean flag) throws NoSuchElementException; 
	
	public AbstractAuthenticationAccountRepository()
	{
		System.out.println(this.getClass().getName() + " created");
	}
	

	@Override
	public String getEncodedPassword(String email)
	{
		UserDetails user = loadUserByUsername(email);
		return user.getPassword();
	}
}