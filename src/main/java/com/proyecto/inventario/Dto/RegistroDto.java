package com.proyecto.inventario.Dto;

import jakarta.validation.constraints.NotBlank;

public class RegistroDto {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String nombreU;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String contraseña;

    public RegistroDto() {
    }

    public RegistroDto(String nombreU, String contraseña) {
        this.nombreU = nombreU;
        this.contraseña = contraseña;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String nombreU) {
        this.nombreU = nombreU;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}