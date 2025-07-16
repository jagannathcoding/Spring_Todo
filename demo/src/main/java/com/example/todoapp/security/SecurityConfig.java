package com.example.todoapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for Postman
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()  // Signup & Login public
                .requestMatchers("/api/todos/**").permitAll() // ✅ Permit todos
                .anyRequest().authenticated();

        return http.build();
    }
}
