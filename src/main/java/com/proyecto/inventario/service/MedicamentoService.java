package com.proyecto.inventario.service;

import java.util.List;

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
        return medicamentoRepository.save(medicamento);
        
    }
}