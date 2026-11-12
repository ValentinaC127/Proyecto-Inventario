package com.proyecto.inventario.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.inventario.Dto.dto;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.service.MedicamentoService;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoRestController {

    private final MedicamentoService medicamentoService;

    public MedicamentoRestController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listarTodos(
            @RequestParam(required = false) String keyword) {
        
        Page<Medicamento> pagina = medicamentoService.obtenerMedicamentosPaginados(
            keyword, PageRequest.of(0, 100)
        );
        return ResponseEntity.ok(pagina.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Medicamento medicamento = medicamentoService.obtenerPorId(id);
            return ResponseEntity.ok(medicamento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Error 404: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearMedicamento(@RequestBody dto dtoObj) {
        try {
            dtoObj.setId(null); 
            Medicamento nuevo = medicamentoService.guardar(dtoObj);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error 400: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedicamento(@PathVariable Long id, @RequestBody dto dtoObj) {
        try {
            dtoObj.setId(id); 
            Medicamento actualizado = medicamentoService.guardar(dtoObj);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No existe")) {
                return ResponseEntity.status(404).body("Error 404: " + e.getMessage());
            }
            return ResponseEntity.badRequest().body("Error 400: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedicamento(@PathVariable Long id) {
        try {
            medicamentoService.eliminar(id);
           return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Error 404: " + e.getMessage());
        }
    }
}