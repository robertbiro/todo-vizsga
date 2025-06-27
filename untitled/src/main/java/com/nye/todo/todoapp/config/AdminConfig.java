package com.nye.todo.todoapp.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AdminConfig {
// from: https://www.youtube.com/watch?v=R4OkfpPK9p0
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;
}
