package com.example.todoapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todoapp.dto.LoginRequest;
import com.example.todoapp.dto.SignupRequest;
import com.example.todoapp.model.User;
import com.example.todoapp.security.JwtUtils;
import com.example.todoapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        try {
            userService.signup(request);
            return ResponseEntity.ok("User registered");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            User user = userService.login(request);
            String token = jwtUtils.generateToken(user.getEmail());

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setSecure(false);

            response.addCookie(cookie);

            return ResponseEntity.ok(user.getName());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(false);

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, User>> getAuthUser(HttpServletRequest request) {
        try {
            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }
            if (token == null) {
                return ResponseEntity.status(401).build();
            }
            String email = jwtUtils.extractUsername(token);
            User user = userService.getUserByEmail(email);

            Map<String, User> response = new HashMap<>();
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
    

}
