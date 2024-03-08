package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.LoginDto;
import com.project.dto.SignInRequest;
import com.project.dto.SignUpRequest;
import com.project.entity.User;
import com.project.enums.Role;
import com.project.repositories.UserRepository;
import com.project.service.AutheticationService;
import com.project.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private AutheticationService authenticationService;

	@Autowired
	private UserRepository userRepository;
	
	

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {

		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(request.getPassword());
		user.setRole(Role.USER);
		String message = authenticationService.signUp(request);

		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginDto> signIn(@RequestBody SignInRequest signInRequest) {
		//System.err.println(signInRequest.getEmail() + " = " + signInRequest.getPassword());
		LoginDto login = authenticationService.signIn(signInRequest);
		return new ResponseEntity<LoginDto>(login, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('USER')")
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> list = userRepository.findAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

}
