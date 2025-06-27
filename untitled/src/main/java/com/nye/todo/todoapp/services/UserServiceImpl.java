package com.nye.todo.todoapp.services;

import com.nye.todo.todoapp.dto.RegisterDTO;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Role;
import com.nye.todo.todoapp.exception.UserNameAlreadyTakenException;
import com.nye.todo.todoapp.exception.UserNameMissingException;
import com.nye.todo.todoapp.exception.UserNotFoundException;
import com.nye.todo.todoapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDTO registerDto) throws UserNameMissingException, UserNameAlreadyTakenException {
        if (registerDto.getUsername() == null || registerDto.getUsername().isBlank()) {
            throw new UserNameMissingException();
        }
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException();
        }
        ApplicationUser user = new ApplicationUser();
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Override
    public ApplicationUser getUserByPrincipal(Principal principal) throws UserNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUsername(principal.getName()).orElseThrow(() ->new UserNotFoundException());
        System.out.println(applicationUser.getUsername() + " form service");
        return applicationUser;
    }

    @Override
    public ApplicationUser getAssigneeByUsername(String assigneeName) throws UserNotFoundException {
        ApplicationUser assignee = userRepository.findByUsername(assigneeName).orElseThrow(() -> new UserNotFoundException());
        return assignee;
    }
    public List<ApplicationUser> listAll(){
        return userRepository.findAll();


    }
    //EntityNotFoundException --> erre mér korábban irtam exceptiont, használjuk azt, akkor egységes
    public ApplicationUser findById(long id){
        Optional<ApplicationUser> applicationUser=userRepository.findById(id);
        if(applicationUser.isPresent()){
            return applicationUser.get();
        }
        else {
            throw new EntityNotFoundException("User not found");
        }
    }

    //delete
    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    //add
    public void addUser(ApplicationUser applicationUser){
        userRepository.save(applicationUser);
    }
}
