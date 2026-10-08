package com.proyecto.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.inventario.Dto.dto; // Importa tu clase dto
import com.proyecto.inventario.model.Laboratorio;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.repository.LaboratorioRepository;
import com.proyecto.inventario.service.MedicamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;
    private final LaboratorioRepository laboratorioRepository;

    public MedicamentoController(MedicamentoService medicamentoService, LaboratorioRepository laboratorioRepository) {
        this.medicamentoService = medicamentoService;
        this.laboratorioRepository = laboratorioRepository;
    }

    @GetMapping({"", "/"})
    public String listarMedicamentos(
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(size = 5) Pageable pageable,
            Model model) {
        
        Page<Medicamento> paginaMedicamentos = medicamentoService.obtenerMedicamentosPaginados(keyword, pageable);
        
        model.addAttribute("medicamentos", paginaMedicamentos.getContent());
        model.addAttribute("paginaActual", paginaMedicamentos);
        model.addAttribute("keyword", keyword);
        
        return "medicamentos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medicamento", new dto()); // Usa tu clase dto
        return "medicamentos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMedicamento(
            @Valid @ModelAttribute("medicamento") dto dtoObj, 
            BindingResult result, 
            Model model,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "medicamentos/formulario";
        }

        try {
            Medicamento medicamento = new Medicamento();
            medicamento.setId(dtoObj.getId());
            medicamento.setNombre(dtoObj.getNombre());
            medicamento.setPrecio(dtoObj.getPrecio());
            medicamento.setStock(dtoObj.getStock());
            medicamento.setFechaVencimiento(dtoObj.getFechaVencimiento());
            
            String nombreLabTrimmed = dtoObj.getLaboratorio().trim();
            Laboratorio laboratorio = laboratorioRepository.findByNombreIgnoreCase(nombreLabTrimmed)
                    .orElseGet(() -> {
                        Laboratorio nuevoLab = new Laboratorio();
                        nuevoLab.setNombre(nombreLabTrimmed);
                        return laboratorioRepository.save(nuevoLab);
                    });
            
            medicamento.setLaboratorio(laboratorio); 

            medicamentoService.guardar(medicamento);
            
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Medicamento guardado con éxito!");
            return "redirect:/medicamentos";
            
        } catch (IllegalArgumentException e) {
            result.rejectValue("fechaVencimiento", "error.fechaVencimiento", e.getMessage());
            return "medicamentos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoService.obtenerPorId(id);

        if (medicamento == null) {
            return "redirect:/medicamentos";
        }

        dto dtoObj = new dto();
        dtoObj.setId(medicamento.getId());
        dtoObj.setNombre(medicamento.getNombre());
        dtoObj.setPrecio(medicamento.getPrecio());
        dtoObj.setStock(medicamento.getStock());
        dtoObj.setFechaVencimiento(medicamento.getFechaVencimiento());
        
        if (medicamento.getLaboratorio() != null) {
            dtoObj.setLaboratorio(medicamento.getLaboratorio().getNombre());
        }

        model.addAttribute("medicamento", dtoObj);
        return "medicamentos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedicamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        medicamentoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "¡Medicamento eliminado correctamente!");
        return "redirect:/medicamentos";
    }
}