package com.nye.todo.todoapp.exception;

import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends TodoException {

    public TodoNotFoundException() {

        super("The todo with this ID doesn't exist", HttpStatus.NOT_FOUND);
    }
}
