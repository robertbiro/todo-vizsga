package com.nye.todo.todoapp.entities;

import lombok.Getter;

@Getter
public enum Tag {

    SURGOS("Sürgős"),
    SPRINT("Sprint"),
    RAER("Ráér");

    private final String label;

    Tag(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
