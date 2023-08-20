package com.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.project.entities.User;
import com.project.repositories.UserRepo;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String enrollment) throws UsernameNotFoundException {
		User user = userRepo.findByEnrollment(enrollment);
	    if (user == null) {
	        throw new UsernameNotFoundException("User Not Found!");
	    }
	    return new UserSecurity(user);
	}

}
