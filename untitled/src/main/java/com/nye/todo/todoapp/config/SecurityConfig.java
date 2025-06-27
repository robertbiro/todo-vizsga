package com.nye.todo.todoapp.config;


import com.nye.todo.todoapp.repositories.UserRepository;
import com.nye.todo.todoapp.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(">>> SecurityFilterChain beállítva!");
        http
                .authorizeHttpRequests(auth -> auth
                        //Ezeket az útvonalakat bárki elérheti, akár be van jelentkezve, akár nem.
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        //Az /admin/** útvonalakat csak olyan felhasználó érheti el, akinek a szerepköre ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                //Bejelentkezéshez használt HTML oldal: /login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/my-profile", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
    //Ez a bean gondoskodik a jelszavak titkosításáról (hash-eléséről).
    //Regisztrációnál és belépésnél ezzel hasonlítja össze a megadott jelszót az adatbázisban tárolttal.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

