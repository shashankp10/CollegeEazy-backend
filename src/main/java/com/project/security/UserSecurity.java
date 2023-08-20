package com.project.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.entities.User;


public class UserSecurity implements UserDetails{
	
private static final long serialVersionUID = 5810564106641421123L;
	
    private final User users;


    public UserSecurity(User users) {
        this.users = users;
    }

   // @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(users.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEnrollment();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
