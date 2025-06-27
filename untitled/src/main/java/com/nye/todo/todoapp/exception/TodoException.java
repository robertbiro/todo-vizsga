package com.nye.todo.todoapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TodoException extends Exception{

    private final HttpStatus httpStatus;

    public TodoException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }



}
