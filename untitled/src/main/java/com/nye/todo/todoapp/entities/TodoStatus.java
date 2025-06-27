package com.nye.todo.todoapp.entities;

public enum TodoStatus {

    IN_PROGRESS("Folyamatban"),
    DONE("Kész");

    private final String label;

    TodoStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
