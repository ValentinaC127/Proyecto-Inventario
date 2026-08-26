package com.proyecto.inventario.controller;

import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.service.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;
    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public String listarMedicamentos(Model model) {
        model.addAttribute("medicamentos", medicamentoService.obtenerTodos());
        return "medicamentos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medicamento", new Medicamento());
        return "medicamentos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMedicamento(@Valid @ModelAttribute("medicamento") Medicamento medicamento, 
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "medicamentos/formulario";
        }
        medicamentoService.guardar(medicamento);
        return "redirect:/medicamentos";
    }
}