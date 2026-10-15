package com.proyecto.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.inventario.Dto.LoginDto;

import jakarta.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "error", required = false) String error, 
            Model model) {
        model.addAttribute("loginDto", new LoginDto());
        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
        }
        return "medicamentos/login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @Valid @ModelAttribute("loginDto") LoginDto loginDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "medicamentos/login";
        }

        return "redirect:/medicamentos";
    }
}