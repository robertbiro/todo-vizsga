package com.nye.todo.todoapp.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends TodoException{
    public AccessDeniedException() {
        super("It is not your task", HttpStatus.NOT_FOUND);
    }
}
