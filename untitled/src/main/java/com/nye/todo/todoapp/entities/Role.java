package com.nye.todo.todoapp.entities;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN , USER;

    public String getToString() {
        return super.toString();
    }
}
