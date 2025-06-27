package com.nye.todo.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class RegisterDTO {

    private String name;
    private String username;
    @Email
    private String email;
    private String password;
}
