package com.ohadr.google_authenticator.repository;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;


public class InMemoryAuthenticationUserImpl implements UserDetails 
{
	private String 		email;
	private String 		password;
	private boolean 	activated;
	private Date 		passwordLastChangeDate;
	private int 		loginAttemptsLeft;
    private final Set<GrantedAuthority> authorities;

	
	public InMemoryAuthenticationUserImpl(
			String username,
			String password,
			boolean activated,
			int	loginAttemptsLeft,
			Date passwordLastChangeDate,
			Collection<? extends GrantedAuthority> authorities)
	{
		this.email = username;
		this.password = password;
		this.activated = activated;
		this.loginAttemptsLeft = loginAttemptsLeft;
		this.passwordLastChangeDate = passwordLastChangeDate;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	@Override
	public String getUsername() 
	{
		return email;
	}


	@Override
	public String getPassword()
	{
		return password;
	}
	
	@Override
	public boolean isEnabled() 
	{
		return activated;
	}


    @Override
    public Collection<GrantedAuthority> getAuthorities() 
    {
        return authorities;
    }

	@Override
	public boolean isAccountNonLocked() 
	{
		return !isAccountLocked();
	}

	/**
	 * account is "locked" if it is disabled, and the num attempts exceeds the max-Attempts.
	 * @return
	 */
	public boolean isAccountLocked() 
	{
		return !isEnabled() && (loginAttemptsLeft <= 0);
	}

	//NOTE and TODO: if i implement this method correctly, then when creds expired the login will fail (bcoz Spring
	//calls this method and then throws CredsExpiredEception). in my flows (not sure it is the right thing), the login
	//is successful and in the successHandler I check if password has expired.
	@Override
	public boolean isCredentialsNonExpired() 
	{
		//TODO calc the passwordLastChangeDate with the time from policy (account-axpiry)
//		passwordLastChangeDate;
		return true;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}


	
    /**
     * copied from org.springframework.security.core.userdetails.User
     * 
     * @param authorities
     * @return
     */
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    /**
     * copied from org.springframework.security.core.userdetails.User
     * 
     */
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}