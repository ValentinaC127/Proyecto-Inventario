package com.proyecto.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.inventario.model.Laboratorio;
import java.util.Optional;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    Optional<Laboratorio> findByNombreIgnoreCase(String nombre);
}