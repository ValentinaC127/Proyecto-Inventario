package com.proyecto.inventario.controller;

import com.proyecto.inventario.Dto.RegistroDto;
import com.proyecto.inventario.model.Registro;
import com.proyecto.inventario.repository.RegistroRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final RegistroRepository registroRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroController(RegistroRepository registroRepository, PasswordEncoder passwordEncoder) {
        this.registroRepository = registroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("registroDto", new RegistroDto());
        return "medicamentos/registro"; 
    }

    @PostMapping
    public String registrarUsuario(
            @Valid @ModelAttribute("registroDto") RegistroDto registroDto, 
            BindingResult result, 
            Model model) {

        if (result.hasErrors()) {
            return "medicamentos/registro"; 
        }

        if (registroRepository.findByNombreU(registroDto.getNombreU()).isPresent()) {
            model.addAttribute("errorUsuario", "El usuario ya existe en la base de datos.");
            return "medicamentos/registro";
        }

        Registro nuevoRegistro = new Registro(
            registroDto.getNombreU(), 
            passwordEncoder.encode(registroDto.getContraseña())
        );

        registroRepository.save(nuevoRegistro);

        return "redirect:/login?registro=true";    
    }
}