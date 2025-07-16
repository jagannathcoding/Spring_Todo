package com.example.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LoginRequest {
	
	@NotBlank(message="Email is required")
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;
	
	

}
