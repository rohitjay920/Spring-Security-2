package com.project.dto;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
public class SignUpRequest {
	String name;
	String email;
	String password;
}
