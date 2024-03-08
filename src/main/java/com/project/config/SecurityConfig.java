package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.filter.JwtAuthenticationFilter;
import com.project.service.UserService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
//for testing using basic auth and no password encoder 
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/api/getAllUsers").permitAll().requestMatchers(HttpMethod.POST,"/api/saveUser").authenticated());
//		http.httpBasic();
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers(HttpMethod.POST, "/api/signup", "/api/login").permitAll()
								// .requestMatchers(HttpMethod.GET,
								// "/user/validate/**").permitAll().anyRequest().authenticated()
								.requestMatchers(HttpMethod.GET, "/api/getAllUsers").hasRole("USER").anyRequest()
								.authenticated())

				// .authenticationProvider(authenticationProvider())
				
				
				// adding a filter to check jwt before UsernamePasswordAuthenticationFilter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return security.build();
	}

//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userService.userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder);
//		return authProvider;
//	}

	// provided a custom authentication provider class for AuthenticationManager
	// Bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
