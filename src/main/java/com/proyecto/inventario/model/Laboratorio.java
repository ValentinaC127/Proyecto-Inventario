package com.proyecto.inventario.model;

import jakarta.persistence.*;

@Entity
@Table(name = "laboratorios")
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    public Laboratorio() {}

    public Laboratorio(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}