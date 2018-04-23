package com.ohadr.google_authenticator.repository;

import java.util.Date;

import org.springframework.security.provisioning.UserDetailsManager;


public interface AuthenticationAccountRepository extends UserDetailsManager
{
	void setEnabled(String email);
	void setDisabled(String email);

	/**
	 * sets a password for a given user
	 * @param email - the user's email
	 * @param newPassword - new password to set
	 */
	void setPassword(String email, String newPassword); 
	
	String getEncodedPassword(String username);
	
	void setAuthority(String username, String authority);

}