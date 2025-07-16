package com.example.todoapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid email format")
	private String Email;
	
	@NotBlank(message="Password is required")
	@Size(min=6,message="Password must be atleast 6 characters")
	private String password;
	
	@NotBlank(message="Name is required")
	private String name;
	

}
