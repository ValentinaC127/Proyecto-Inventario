package com.proyecto.inventario.controller;

import com.proyecto.inventario.model.Registro;
import com.proyecto.inventario.repository.RegistroRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    private final RegistroRepository registroRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroController(
            RegistroRepository registroRepository,
            PasswordEncoder passwordEncoder) {
        this.registroRepository = registroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("nombreU") String nombreU,
            @RequestParam("contraseña") String contraseña) {


        if (registroRepository.findByNombreU(nombreU).isPresent()) {
            return "redirect:/registro?error=registro";
        }

        Registro nuevoRegistro = new Registro(
            nombreU, 
            passwordEncoder.encode(contraseña)
        );

    
        registroRepository.save(nuevoRegistro);

        return "redirect:/login?registro=exitoso";
    }
}