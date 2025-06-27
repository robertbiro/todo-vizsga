package com.nye.todo.todoapp.controller;

import com.nye.todo.todoapp.dto.RegisterDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.exception.TodoException;
import com.nye.todo.todoapp.exception.UserNotFoundException;
import com.nye.todo.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
//ezeket használja a program!!!!!!!!!!!!!!!!!!!
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {
    @Autowired
    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {

        return "login";
    };

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("applicationUser", new ApplicationUser());
        return "registration/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("registerDto") RegisterDTO registerDto, Model model) {
        try {
            userService.register(registerDto);
            return "redirect:/login?registered";
        } catch (TodoException todoException) {
            model.addAttribute("error", todoException.getMessage());
            return "registration/register";
        }
    }

    @GetMapping("/my-profile")
    public String profile(Model model, Principal principal) throws UserNotFoundException {
        System.out.println("BEJELENTKEZETT: " + principal.getName());
        ApplicationUser user = userService.getUserByPrincipal(principal);
        System.out.println("SZEREPKÖR: " + user.getRole());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/redirect")
    public String redirectAfterLogin(Principal principal) throws UserNotFoundException {
        ApplicationUser user = userService.getUserByPrincipal(principal);
        return switch (user.getRole()) {
            case ADMIN -> "redirect:/admin/users";
            case USER -> "redirect:/todo/list";
        };
    }
}

