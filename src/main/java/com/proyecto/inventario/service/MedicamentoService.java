package com.proyecto.inventario.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.inventario.Dto.dto;
import com.proyecto.inventario.model.Laboratorio;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.repository.LaboratorioRepository;
import com.proyecto.inventario.repository.MedicamentoRepository;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final LaboratorioRepository laboratorioRepository;

    public MedicamentoService(
            MedicamentoRepository medicamentoRepository,
            LaboratorioRepository laboratorioRepository) {

        this.medicamentoRepository = medicamentoRepository;
        this.laboratorioRepository = laboratorioRepository;
    }

    @Transactional(readOnly = true)
    public Page<Medicamento> obtenerMedicamentosPaginados(
            String keyword,
            Pageable pageable) {

        String busqueda = keyword == null ? "" : keyword.trim();

        return medicamentoRepository.buscarPorNombreOLaboratorio(
                busqueda, pageable);
    }

    @Transactional
    public Medicamento guardar(dto dtoObj) {

        if (dtoObj.getFechaVencimiento() == null
                || !dtoObj.getFechaVencimiento()
                          .isAfter(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "La fecha de vencimiento debe ser posterior a la fecha actual.");
        }

        if (dtoObj.getLaboratorio() == null
                || dtoObj.getLaboratorio().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El laboratorio es obligatorio.");
        }

        String nombreLaboratorio = dtoObj.getLaboratorio().trim();

        
        Laboratorio laboratorio = laboratorioRepository
                .findByNombreIgnoreCase(nombreLaboratorio)
                .orElseGet(() -> {
                    Laboratorio nuevoLaboratorio = new Laboratorio();
                    nuevoLaboratorio.setNombre(nombreLaboratorio);

                    return laboratorioRepository.save(nuevoLaboratorio);
                });

        Medicamento medicamento;

        if (dtoObj.getId() != null) {

            medicamento = medicamentoRepository
                    .findById(dtoObj.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe el medicamento que deseas editar."));

        } else {
            medicamento = new Medicamento();
        }

        medicamento.setNombre(dtoObj.getNombre().trim());
        medicamento.setLaboratorio(laboratorio);
        medicamento.setPrecio(dtoObj.getPrecio());
        medicamento.setStock(dtoObj.getStock());
        medicamento.setFechaVencimiento(
                dtoObj.getFechaVencimiento());

        return medicamentoRepository.save(medicamento);
    }

    @Transactional(readOnly = true)
    public Medicamento obtenerPorId(Long id) {

        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Medicamento no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminar(Long id) {

        if (!medicamentoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar. El medicamento no existe.");
        }

        medicamentoRepository.deleteById(id);
    }
}
