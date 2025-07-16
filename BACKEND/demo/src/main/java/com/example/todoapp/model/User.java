package com.example.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	private String name;
	
	@Column(unique=true)
	private String email;
	
	private String password;
}
