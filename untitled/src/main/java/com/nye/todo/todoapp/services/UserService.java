package com.nye.todo.todoapp.services;


import com.nye.todo.todoapp.dto.RegisterDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.exception.UserNameAlreadyTakenException;
import com.nye.todo.todoapp.exception.UserNameMissingException;
import com.nye.todo.todoapp.exception.UserNotFoundException;

import java.security.Principal;

public interface UserService {
    void register(RegisterDTO registerDto) throws UserNameMissingException, UserNameAlreadyTakenException;
    ApplicationUser getUserByPrincipal(Principal principal) throws UserNotFoundException;
    ApplicationUser getAssigneeByUsername (String assigneeName) throws UserNotFoundException;

}
