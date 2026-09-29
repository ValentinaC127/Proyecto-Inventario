package com.proyecto.inventario.service;

import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.repository.MedicamentoRepository;

 
@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> obtenerTodos() {
        return medicamentoRepository.findAll();
    }

    public Medicamento guardar(Medicamento medicamento) {
      if (medicamento.getFechaVencimiento() == null || !medicamento.getFechaVencimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha asignada debe ser posterior a la fecha actual.");
        }
        return medicamentoRepository.save(medicamento);
    }
    public Medicamento obtenerPorId(Long id) {
    return medicamentoRepository.findById(id).orElseThrow(() 
    -> new IllegalArgumentException("Medicamento no encontrado con el ID: " + id));
}

    public void eliminar(Long id) {
    medicamentoRepository.deleteById(id);
 }
}