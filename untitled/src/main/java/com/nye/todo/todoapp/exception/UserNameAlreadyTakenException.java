package com.nye.todo.todoapp.exception;

import org.springframework.http.HttpStatus;

public class UserNameAlreadyTakenException extends TodoException {

    public UserNameAlreadyTakenException() {
        super("The username is already taken" ,HttpStatus.BAD_REQUEST);
    }
}
