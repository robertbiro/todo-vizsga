package com.nye.todo.todoapp.repositories;

import com.nye.todo.todoapp.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository <Todo, Long> {
    //https://www.youtube.com/watch?v=GpqJzWCcQXY&ab_channel=ProgrammingKnowledge

    @Query("SELECT todo From Todo todo Where todo.assignee.username = ?1")
    List<Todo> findByAssigneeName(String assignee);
}
