package com.example.todoapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed = false;

    @Column(name = "user_todo_id")
    private Integer userTodoId; // New column for per-user todo ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
