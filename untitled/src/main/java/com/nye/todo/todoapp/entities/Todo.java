package com.nye.todo.todoapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 1000)
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.IN_PROGRESS;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private ApplicationUser author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private ApplicationUser assignee;

    @Enumerated(EnumType.STRING)
    private Tag tag;




}
