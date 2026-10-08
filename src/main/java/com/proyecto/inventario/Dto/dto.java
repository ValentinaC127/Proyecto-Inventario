package com.proyecto.inventario.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public class dto {

    private Long id;

    @NotBlank(message = "El nombre del medicamento es obligatorio.")
    private String nombre;

    @NotBlank(message = "El laboratorio es obligatorio.")
    private String laboratorio; 

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a cero.")
    private Double precio;

    @NotNull(message = "El stock es obligatorio.")
    @PositiveOrZero(message = "El stock no puede ser negativo.")
    private Integer stock;

    @NotNull(message = "La fecha de vencimiento es obligatoria.")
    private LocalDate fechaVencimiento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}