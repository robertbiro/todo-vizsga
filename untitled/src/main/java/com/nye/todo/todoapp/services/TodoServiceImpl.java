package com.nye.todo.todoapp.services;

import com.nye.todo.todoapp.dto.TodoDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Todo;
import com.nye.todo.todoapp.exception.AccessDeniedException;
import com.nye.todo.todoapp.exception.TodoNotFoundException;
import com.nye.todo.todoapp.exception.UserNotFoundException;
import com.nye.todo.todoapp.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserService userService;


    @Override
    public void createTodo(TodoDTO dto, ApplicationUser author) throws UserNotFoundException {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCreatedAt(LocalDateTime.now());
        todo.setDueDate(dto.getDueDate());
        todo.setStatus(dto.getTodoStatus());
        todo.setAuthor(author);
        System.out.println("Beérkezett assignee név: " + dto.getAssigneeName());
        ApplicationUser assignee = userService.getAssigneeByUsername(dto.getAssigneeName());
        todo.setAssignee(assignee);
        System.out.println(assignee.getUsername() + " the task is added to this user");
        todo.setTag(dto.getTag());
        todoRepository.save(todo);
        }
    @Override
    public void deleteTodo(Long todoId) throws TodoNotFoundException {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if(optionalTodo.isPresent()) {
            Todo deletedTodo =todoRepository.findById(todoId).get();
            todoRepository.delete(deletedTodo);
        } else {
            throw new TodoNotFoundException();
        }
    }

    @Override
    public Todo getTodoByID(Long todoId) throws TodoNotFoundException {
        return todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);
    }

    @Override
    public void setDoneAndFinishTime(TodoDTO todoDTO, Todo updatedTodo) {
        System.out.println("todo dto from service" + todoDTO.getTodoStatus().getLabel());
        System.out.println("update dto from service" + updatedTodo.getStatus().getLabel());
        if(!todoDTO.getTodoStatus().equals(updatedTodo.getStatus())) {
            updatedTodo.setStatus(todoDTO.getTodoStatus());
        }
        if (todoDTO.getTodoStatus().getLabel().equals("Kész")) {
            updatedTodo.setCompletedAt(LocalDateTime.now());
            System.out.println(updatedTodo.getCompletedAt() + " from service");
        } else {
            updatedTodo.setCompletedAt(null);
        }
    }
//
    @Override
    public void updateTodo(Long todoId, TodoDTO todoDTO) throws TodoNotFoundException, UserNotFoundException {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if(optionalTodo.isPresent()) {
            Todo updatedTodo = optionalTodo.get();
            System.out.println(" id from service to update: " + todoDTO);
            if(todoDTO.getTitle() != null && !todoDTO.getTitle().isBlank()) {
                updatedTodo.setTitle(todoDTO.getTitle());
            }
            if(todoDTO.getDescription() != null && !todoDTO.getDescription().isBlank()) {
                updatedTodo.setDescription(todoDTO.getDescription());
            }
            if (todoDTO.getDueDate() != null) {
                updatedTodo.setDueDate(todoDTO.getDueDate());
            }
            if (todoDTO.getAssigneeName() != null && !todoDTO.getAssigneeName().isBlank()) {
                ApplicationUser newApplicationUser = userService.getAssigneeByUsername(todoDTO.getAssigneeName());
                updatedTodo.setAssignee(newApplicationUser);
            }
            updatedTodo.setTag(todoDTO.getTag());
            setDoneAndFinishTime(todoDTO, updatedTodo);
            todoRepository.save(updatedTodo);
        } else {
            throw new TodoNotFoundException();
        }
    }


    @Override
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    @Override
    public void setDoneByAssignee(Long todoId, TodoDTO todoDTO, ApplicationUser user) throws TodoNotFoundException, AccessDeniedException, UserNotFoundException {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if(optionalTodo.isPresent()) {
            ApplicationUser currentUser = userService.getAssigneeByUsername(user.getUsername());
            System.out.println(currentUser.getUsername() + " currentiser from serice");
            if (!optionalTodo.get().getAssignee().getId().equals(currentUser.getId())) {
                throw new AccessDeniedException();
            } else {
                Todo updatedTodo = optionalTodo.get();
                System.out.println(optionalTodo.get().getStatus().getLabel() + "  hello from else.1....");
                setDoneAndFinishTime(todoDTO, updatedTodo);
                System.out.println(optionalTodo.get().getStatus().getLabel() + "  hello from else..2...");
                todoRepository.save(updatedTodo);
            }
        } else {
            throw new TodoNotFoundException();
        }
    }

}
