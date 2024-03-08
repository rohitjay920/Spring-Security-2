package com.project.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SignInRequest {
	private String email;
	private String password;
}
