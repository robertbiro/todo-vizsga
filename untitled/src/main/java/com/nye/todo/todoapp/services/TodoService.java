package com.nye.todo.todoapp.services;

import com.nye.todo.todoapp.dto.TodoDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Todo;
import com.nye.todo.todoapp.exception.AccessDeniedException;
import com.nye.todo.todoapp.exception.TodoNotFoundException;
import com.nye.todo.todoapp.exception.UserNotFoundException;

import java.util.List;

public interface TodoService {

    void createTodo(TodoDTO dto, ApplicationUser author) throws UserNotFoundException;
    void deleteTodo(Long todoId) throws TodoNotFoundException;
    void updateTodo(Long todoId, TodoDTO dto) throws TodoNotFoundException, UserNotFoundException;

    Todo getTodoByID(Long todoId) throws  TodoNotFoundException;
    void setDoneAndFinishTime(TodoDTO todoDTO, Todo updatedTodo);
    List<Todo> findAll();
    public void setDoneByAssignee(Long todoId, TodoDTO todoDTO, ApplicationUser user) throws TodoNotFoundException, AccessDeniedException, UserNotFoundException;
}
