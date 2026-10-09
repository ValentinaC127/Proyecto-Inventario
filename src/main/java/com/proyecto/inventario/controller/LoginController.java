package com.proyecto.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.inventario.Dto.LoginDto;

import jakarta.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
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