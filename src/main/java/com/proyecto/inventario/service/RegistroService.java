package com.proyecto.inventario.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.inventario.model.Registro;
import com.proyecto.inventario.repository.RegistroRepository;

@Service
public class RegistroService implements UserDetailsService {

    private final RegistroRepository registroRepository;

    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Registro registro = registroRepository.findByNombreU(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(registro.getNombreU())
                .password(registro.getContraseña()) 
                .roles("USER")
                .build();
    }
}