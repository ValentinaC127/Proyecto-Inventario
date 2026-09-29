package com.proyecto.inventario.repository;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.inventario.model.Medicamento;


public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByStockLessThan(int limiteMinimo);
    List<Medicamento> findByFechaVencimientoAfter(LocalDate fecha);
}