package com.example.todoapp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+86400000))
				.signWith(SignatureAlgorithm.HS512,jwtSecret)
				.compact();
	}
	
	
	 public String extractUsername(String token) {
	        return Jwts.parser()
	                .setSigningKey(jwtSecret)
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	
	
	
	
}
