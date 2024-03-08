package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private UserService userService; 

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 	String username = authentication.getName();
	        String password = authentication.getCredentials().toString();
	       
	     
	        // Implement your custom authentication logic here
	        // For example, fetching user details from a database
	        UserDetails userDetails =  userService.userDetailsService().loadUserByUsername(username);
	      //  UserDetails userDetails = getUserDetailsByUsername(username);

	        // Compare the provided password with the user's actual password
	        if (!userDetails.getPassword().equals(password)) {
	            throw new BadCredentialsException("Invalid username or password");
	        }

	        // If authentication is successful, return a fully authenticated Authentication object
	        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
