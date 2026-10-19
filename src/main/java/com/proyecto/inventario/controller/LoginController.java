package com.proyecto.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.inventario.Dto.LoginDto;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error, 
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
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "medicamentos/login";
        }

        session.setAttribute("usuario", loginDto.getNombreU());
        redirectAttributes.addFlashAttribute("mensajeExito", "¡Bienvenido, " + loginDto.getNombreU() + "!");
        return "redirect:/medicamentos";
    }

    @PostMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}