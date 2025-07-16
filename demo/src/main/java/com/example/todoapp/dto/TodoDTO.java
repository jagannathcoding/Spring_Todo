package com.example.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoDTO {

    @NotBlank(message = "Title is required")
    private String title;
}
