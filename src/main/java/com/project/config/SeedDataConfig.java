package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.entity.User;
import com.project.enums.Role;
import com.project.repositories.UserRepository;
import com.project.service.UserService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SeedDataConfig implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	

	@Override
	public void run(String... args) throws Exception {
		//run method will get invoked when the Spring boot application is running and this data will be added to the DB
		// we use this to create admin and admin credentials
		
		
		if(userRepository.count()==0) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setName("Rohit");
			user.setPassword("admin123");
			user.setRole(Role.ADMIN);
			userRepository.save(user);
		}
	
		

		
		//Using Builder pattern to configure the admin credentials 
		//Use @Builder on top of User entity
		//@Builder belongs to lombok hence lombok must be installed in the system and dependency must be added 
		
//		if(userRepository.count()==0) {
//			User user = User.builder().email("admin@gmail.com").name("Rohit").password(passwordEncoder.encode("admin123")).role(Role.ADMIN).build();
//			userRepository.save(user);
//			
//			log.debug("created ADMIN user - {}",user);
//		}
	
	}
	
	
}
