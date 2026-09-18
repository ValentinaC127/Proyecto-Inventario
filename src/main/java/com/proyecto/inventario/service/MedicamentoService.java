package com.proyecto.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.inventario.Dto.dto;
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

    public Medicamento guardar(dto datos) {

        Medicamento medicamento = new Medicamento();

        medicamento.setNombre(datos.getNombre());
        medicamento.setLaboratorio(datos.getLaboratorio());
        medicamento.setPrecio(datos.getPrecio());
        medicamento.setStock(datos.getStock());

        medicamento.setFechaVencimiento(
                LocalDate.parse(datos.getFechaVencimiento())
        );

        return medicamentoRepository.save(medicamento);
    }
}