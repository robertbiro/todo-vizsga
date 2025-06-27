package com.nye.todo.todoapp.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException  extends  TodoException{

    public UserNotFoundException() {
        super("The user is not registered yet", HttpStatus.NOT_FOUND);
    }
}
