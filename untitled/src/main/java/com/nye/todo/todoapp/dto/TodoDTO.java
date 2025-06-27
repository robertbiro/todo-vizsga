package com.nye.todo.todoapp.dto;

import com.nye.todo.todoapp.entities.Tag;
import com.nye.todo.todoapp.entities.TodoStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TodoStatus todoStatus = TodoStatus.IN_PROGRESS;
    private LocalDateTime completedAt;
    private String assigneeName;
    @Enumerated(EnumType.STRING)
    private Tag tag = Tag.SURGOS;
}
