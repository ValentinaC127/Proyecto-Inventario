package com.proyecto.inventario.Dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
     @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String nombreL;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String contraseña;

    public LoginDto() {
    }

    public LoginDto(String nombreL, String contraseña) {
        this.nombreL = nombreL;
        this.contraseña = contraseña;
    }

    public String getNombreL() {
        return nombreL;
    }

    public void setNombreL(String nombreL) {
        this.nombreL = nombreL;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
