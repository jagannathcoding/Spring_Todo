package com.example.todoapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.TodoRepository;
import com.example.todoapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<Todo> getTodos(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return todoRepository.findByUser(user);
    }

    public Todo addTodo(TodoDTO dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the current highest userTodoId for this user
        Integer maxUserTodoId = todoRepository.findMaxUserTodoIdByUser(user);
        int nextUserTodoId = (maxUserTodoId == null) ? 1 : maxUserTodoId + 1;

        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setUser(user);
        todo.setUserTodoId(nextUserTodoId);
        return todoRepository.save(todo);
    }

    public Todo markTodoCompleted(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized to update this todo");
        }

        todo.setCompleted(true);
        return todoRepository.save(todo);
    }
}
