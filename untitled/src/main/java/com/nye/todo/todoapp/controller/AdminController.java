package com.nye.todo.todoapp.controller;

import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Role;
import com.nye.todo.todoapp.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//ebbol mit használ a program????
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImpl userService;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public String listAll(Model model){
        List<ApplicationUser> users=userService.listAll();
        model.addAttribute("users",users);
        return "admin/listusers";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id,Model model)
    {
        userService.deleteById(id);
        List<ApplicationUser> users=userService.listAll();
        model.addAttribute("users",users);
        return "admin/listusers";

    }
    //pontosan ez mit is csinál? Az amind tud usert hozzáadni? De a user tud saját maga regsitrálni
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("applicationUser", new ApplicationUser());
        return "admin/adduser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("applicationUser") ApplicationUser applicationUser){
        applicationUser.setRole(Role.USER);
        userService.addUser(applicationUser);
        return "redirect:/admin/users";
    }



}
