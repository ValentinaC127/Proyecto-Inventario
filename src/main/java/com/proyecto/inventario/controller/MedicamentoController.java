package com.proyecto.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.inventario.Dto.dto;
import com.proyecto.inventario.model.MForm;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.service.MedicamentoService;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/medicamentos")

public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public String listarMedicamentos(Model model) {

        model.addAttribute(
                "medicamentos",medicamentoService.obtenerTodos());
        return "medicamentos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute("medicamento", new MForm());

        return "medicamentos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMedicamento(
            @Valid @ModelAttribute("medicamento") MForm formulario,
            BindingResult result) {

        if (result.hasErrors()) {
            return "medicamentos/formulario";
        }

        dto datos = new dto();

        datos.setId(formulario.getId());
        datos.setNombre(formulario.getNombre());
        datos.setLaboratorio(formulario.getLaboratorio());
        datos.setPrecio(formulario.getPrecio());
        datos.setStock(formulario.getStock());
        datos.setFechaVencimiento(
                formulario.getFechaVencimiento().toString()
        );

        medicamentoService.guardar(datos);

        return "redirect:/medicamentos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoService.obtenerPorId(id);
        
        if (medicamento == null) {
            return "redirect:/medicamentos";
        }

        MForm formulario = new MForm();
        formulario.setId(medicamento.getId()); 
        formulario.setNombre(medicamento.getNombre());
        formulario.setLaboratorio(medicamento.getLaboratorio());
        formulario.setPrecio(medicamento.getPrecio());
        formulario.setStock(medicamento.getStock());
        formulario.setFechaVencimiento(medicamento.getFechaVencimiento());

        model.addAttribute("medicamento", formulario);
        return "medicamentos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedicamento(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return "redirect:/medicamentos";
    }   


}