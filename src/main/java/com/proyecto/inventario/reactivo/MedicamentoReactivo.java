package com.proyecto.inventario.reactivo;

public record MedicamentoReactivo(
    Long id,
    String nombre,
    double precio,
    boolean activo
) {}