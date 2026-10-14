package com.proyecto.inventario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.inventario.model.Medicamento;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    @Query("""
        SELECT m 
        FROM Medicamento m 
        LEFT JOIN m.laboratorio l 
        WHERE :keyword IS NULL 
           OR TRIM(:keyword) = '' 
           OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) 
           OR LOWER(l.nombre) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY m.nombre ASC
        """)
    Page<Medicamento> buscarPorNombreOLaboratorio(
            @Param("keyword") String keyword, 
            Pageable pageable
    );
}