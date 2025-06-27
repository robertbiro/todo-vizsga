package com.nye.todo.todoapp.services;

import com.nye.todo.todoapp.entities.ApplicationUser;
import com.nye.todo.todoapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("userDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    //Ez a belépés pillanatában fut le, amikor valaki beírja a felhasználónevét és jelszavát.
    //Lekéri az adatbázisból a felhasználót (ApplicationUser) a megadott username alapján.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("BEJELENTKEZÉSI PRÓBÁLAT: " + username);
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user is not exist " + username));
        //A role értékéből létrehoz egy jogosultságot: ADMIN vagy User
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        //Visszaad egy org.springframework.security.core.userdetails.User példányt:
        // tartalmazza: felhasználónév, titkosított jelszó, szerepkör(ök).
        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }
}
