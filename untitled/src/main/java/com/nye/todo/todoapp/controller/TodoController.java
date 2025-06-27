package com.nye.todo.todoapp.controller;

import com.nye.todo.todoapp.dto.TodoDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Role;
import com.nye.todo.todoapp.entities.Todo;
import com.nye.todo.todoapp.exception.TodoException;
import com.nye.todo.todoapp.exception.TodoNotFoundException;
import com.nye.todo.todoapp.services.TodoService;
import com.nye.todo.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
//ezeket használja a program!!!!!!!!!!!
@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final UserService userService;


    @GetMapping({"", "/list"})
    public String findAll(Model model) {
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "todo/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("todoDto", new TodoDTO());
        return "admin/todo-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute("todoDto") TodoDTO dto,
                                   Principal principal,
                                   Model model) {
        try {
            ApplicationUser admin = userService.getUserByPrincipal(principal);
            System.out.println("hello from controller " + admin.getUsername());

            if (admin.getRole() != Role.ADMIN) {
                model.addAttribute("error", "Csak admin felhasználó hozhat létre feladatot.");
                return "error/access-denied";
            }
            System.out.println(dto.getTitle());
            todoService.createTodo(dto, admin);
            return "redirect:/todo/list";

        } catch (TodoException todoException) {
            model.addAttribute("error", todoException.getMessage());
            return "admin/todo-form";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id, Principal principal, Model model) {
        try {
            ApplicationUser admin = userService.getUserByPrincipal(principal);
            if (admin.getRole() != Role.ADMIN) {
                model.addAttribute("error", "Csak admin törölhet feladatot.");
                return "error/access-denied";
            }
            todoService.deleteTodo(id);
            return "redirect:/todo/list";
        } catch (TodoException todoException) {
            model.addAttribute("error", "Hiba történt a törlés közben: " + todoException.getMessage());
            return "error/access-denied";
        }
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, Principal principal) {
        try {
            ApplicationUser user = userService.getUserByPrincipal(principal);
            Todo currentTodo = todoService.getTodoByID(id);
            System.out.println(id + " updated id from controller");
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTitle(currentTodo.getTitle());
            todoDTO.setDescription(currentTodo.getDescription());
            todoDTO.setDueDate(currentTodo.getDueDate());
            todoDTO.setTodoStatus(currentTodo.getStatus());
            System.out.println("todo dto from controller " + todoDTO.getTodoStatus().getLabel() );
            todoDTO.setAssigneeName(currentTodo.getAssignee().getUsername());
            todoDTO.setTag(currentTodo.getTag());
            model.addAttribute("todoDto", todoDTO);
            model.addAttribute("todoID", id);
            if (user.getRole() == Role.ADMIN) {
                return "admin/edit-todo";
            } else {
                return "user/edit-todo";
            }

        } catch (TodoException todoException) {
            model.addAttribute("error", todoException.getMessage());
            return "error/access-denied";
        }

    }

    @PostMapping("/edit/{id}")
    public String handleUpdateForm(@PathVariable("id") Long id,
                                   @ModelAttribute("todoDto") TodoDTO todoDTO,
                                   Model model,
                                   Principal principal) {
        try {
            ApplicationUser admin = userService.getUserByPrincipal(principal);
            if (admin.getRole() != Role.ADMIN) {
                model.addAttribute("error", "Csak admin törölhet feladatot.");
                return "error/access-denied";
            }
            todoService.updateTodo(id, todoDTO);
            return findAll(model);

        } catch (TodoException todoException) {
            model.addAttribute("error", todoException.getMessage());
            return "error/access-denied";
        }

    }

    @PostMapping("/mark-doneByUser/{id}")
    public String markTodoAsDone(@PathVariable("id") Long id,
                                 @ModelAttribute("todoDto") TodoDTO todoDTO,
                                 Principal principal,
                                 Model model) {
        try {
            ApplicationUser user = userService.getUserByPrincipal(principal);
            todoService.setDoneByAssignee(id, todoDTO, user);
            return findAll(model);
        } catch (TodoException e) {
            model.addAttribute("error", e.getMessage());
            return "error/access-denied";
        }
    }



}
