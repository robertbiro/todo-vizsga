package com.nye.todo.todoapp.controller;

import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.exception.UserNotFoundException;
import com.nye.todo.todoapp.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
//szerintem ezeket nem használja
@Controller
@RequestMapping("/users")
public class UserController {
    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String listAll(Model model){
        List<ApplicationUser> users=userService.listAll();
        model.addAttribute("users",users);
        return "user/listusers";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable("id") long id, Model model, Principal principal) {
        ApplicationUser profileUser = userService.findById(id);
        ApplicationUser currentUser = null;
        try {
            currentUser = userService.getUserByPrincipal(principal);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("user", profileUser);
        model.addAttribute("currentUser", currentUser);

        return "user/userdetails";
    }


}
