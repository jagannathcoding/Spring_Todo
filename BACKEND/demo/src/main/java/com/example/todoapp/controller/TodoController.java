package com.example.todoapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.model.Todo;
import com.example.todoapp.security.JwtUtils;
import com.example.todoapp.service.TodoService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	private final JwtUtils jwtUtils;
	
	
	@GetMapping
	public List<Todo> getTodos(HttpServletRequest request)
	{
		String token=extractTokenFromCookie(request);
		String email=jwtUtils.extractUsername(token);
		return todoService.getTodos(email);
	}
	
	
	@PostMapping
	public Todo addTodo(@Valid @RequestBody TodoDTO dto,HttpServletRequest request) {
		String token=extractTokenFromCookie(request);
		String email=jwtUtils.extractUsername(token);
		return todoService.addTodo(dto,email);
	}
	
	
	@PutMapping("/{id}/complete")
	public Todo markCompleted(@PathVariable Long id, HttpServletRequest request) {
	    String token = extractTokenFromCookie(request);
	    String email = jwtUtils.extractUsername(token);
	    return todoService.markTodoCompleted(id, email);
	}

	
	
	
	
	private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new RuntimeException("JWT token not found");
    }
}