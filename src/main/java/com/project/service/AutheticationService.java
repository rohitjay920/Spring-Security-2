package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.project.dto.LoginDto;
import com.project.dto.SignInRequest;
import com.project.dto.SignUpRequest;
import com.project.entity.User;
import com.project.enums.Role;
import com.project.repositories.UserRepository;
import com.project.utils.JwtUtils;

@Service
public class AutheticationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	public String signUp(SignUpRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setPassword(signUpRequest.getPassword());
		user.setRole(Role.USER);
		
		User savedUser = userRepository.save(user);
		
//		String jwt = jwtUtils.generateToken(user);
//		System.err.println(jwt);
		
		return "The user is successfully saved";
	}
	
	
	public LoginDto signIn(SignInRequest signInRequest) {
		System.err.println(signInRequest.getEmail() + " = " + signInRequest.getPassword());
		
		//this works for encoded password, since we are not encoding password this doesnt work hence a CustomAuthenticationProvider is designed
	/*
	 * 	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		System.err.println(authenticationManager);
	 */
		
		User user = userRepository.findByEmail(signInRequest.getEmail());
		
		String jwt = jwtUtils.generateToken(user);
		
		LoginDto login = new LoginDto();
		login.setJwt(jwt);
		login.setRole(user.getRole());
		
		return login;
	}
}
