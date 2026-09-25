package com.proyecto.inventario.repository;

import com.proyecto.inventario.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Optional<Registro> findByNombreU(String nombreU);
}