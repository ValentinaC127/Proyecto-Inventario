package com.proyecto.inventario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.inventario.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    
    Page<Medicamento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}