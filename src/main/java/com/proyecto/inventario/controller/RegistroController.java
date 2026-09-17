package com.proyecto.inventario.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public RegistroController(
            InMemoryUserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder) {

        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String username,
            @RequestParam String password) {

        if (!password.equals(password)) {
            return "redirect:/registro?error=password";
        }

        if (userDetailsManager.userExists(username)) {
            return "redirect:/registro?error=usuario";
        }

        var nuevoUsuario = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();

        userDetailsManager.createUser(nuevoUsuario);

        return "redirect:/login?registro=exitoso";
    }
}