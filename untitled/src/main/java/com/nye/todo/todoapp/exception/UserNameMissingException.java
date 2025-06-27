package com.nye.todo.todoapp.exception;

import org.springframework.http.HttpStatus;

public class UserNameMissingException extends TodoException {

    public UserNameMissingException() {
        super("Missing username!", HttpStatus.BAD_REQUEST);
    }
}
