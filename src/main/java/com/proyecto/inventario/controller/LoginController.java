package com.proyecto.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "medicamentos/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "medicamentos/registro";
    }

    public void iniciarSesion() {
        System.out.println("Proceso de inicio de sesión");
    }
}