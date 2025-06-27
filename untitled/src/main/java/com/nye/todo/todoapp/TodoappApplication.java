package com.nye.todo.todoapp;

import com.nye.todo.todoapp.config.AdminConfig;
import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.entities.Role;
import com.nye.todo.todoapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoappApplication implements CommandLineRunner {

	private final AdminConfig adminConfig;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String adminUsername = adminConfig.getAdminUsername();
		String adminPassword = adminConfig.getAdminPassword();


		if(!userRepository.findByUsername(adminUsername).isPresent()) {
			ApplicationUser adminUser = new ApplicationUser();
			adminUser.setUsername(adminUsername);
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
			adminUser.setEmail("admin@admin.com");
			adminUser.setRole(Role.ADMIN);
			userRepository.save(adminUser);
		}
	}

}
