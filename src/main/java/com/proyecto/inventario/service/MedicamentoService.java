package com.proyecto.inventario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.repository.MedicamentoRepository;
import java.time.LocalDate;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Page<Medicamento> obtenerMedicamentosPaginados(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return medicamentoRepository.findByNombreContainingIgnoreCase(keyword, pageable);
        }
        return medicamentoRepository.findAll(pageable);
    }

    public Medicamento guardar(Medicamento medicamento) {
        if (medicamento.getFechaVencimiento() == null || !medicamento.getFechaVencimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser posterior a la fecha actual.");
        }
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento obtenerPorId(Long id) {
        return medicamentoRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Medicamento no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        medicamentoRepository.deleteById(id);
    }
}