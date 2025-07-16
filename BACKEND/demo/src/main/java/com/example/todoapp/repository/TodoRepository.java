package com.example.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser(User user);

    @Query("SELECT MAX(t.userTodoId) FROM Todo t WHERE t.user = :user")
    Integer findMaxUserTodoIdByUser(@Param("user") User user);
}
