package com.proyecto.inventario.Dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegistroDto {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String nombreU;

    @Email(message = "Ingrese un formato de correo electronico valido.")
    private String correoElectronico;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String contraseña;

    public RegistroDto() {
    }
    public RegistroDto(String nombreU, String contraseña, String correoElectronico) {
        this.nombreU = nombreU;
        this.contraseña = contraseña;
        this.correoElectronico = correoElectronico;

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
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}