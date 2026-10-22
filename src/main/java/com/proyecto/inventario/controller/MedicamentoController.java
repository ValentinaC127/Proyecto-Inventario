package com.proyecto.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.proyecto.inventario.Dto.dto;
import com.proyecto.inventario.model.Medicamento;
import com.proyecto.inventario.service.MedicamentoService;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public String listarMedicamentos(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @CookieValue(name = "tema", defaultValue = "claro") String tema,
            Model model) {

        int pagina = Math.max(page, 0);
        Pageable pageable = PageRequest.of(pagina, 5);

        Page<Medicamento> medicamentos =
                medicamentoService.obtenerMedicamentosPaginados(
                        keyword, pageable);

        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("paginaActual", medicamentos.getNumber());
        model.addAttribute("totalPaginas", medicamentos.getTotalPages());
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        
        model.addAttribute("temaActual", tema);

        return "medicamentos/lista";
    }

    @PostMapping("/preferencia")
    public String guardarPreferenciaTema(
            @RequestParam String tema, 
            HttpServletResponse response) {
        
        String elegido = tema.equals("oscuro") ? "oscuro" : "claro";
        
        Cookie cookie = new Cookie("tema", elegido);
        cookie.setMaxAge(180); 
        cookie.setPath("/");        
        cookie.setHttpOnly(true);   
        
        response.addCookie(cookie);
        
        return "redirect:/medicamentos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute("medicamento", new dto());

        return "medicamentos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMedicamento(
            @Valid @ModelAttribute("medicamento") dto dtoObj,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            return "medicamentos/formulario";
        }

        try {
            medicamentoService.guardar(dtoObj);
            redirectAttributes.addFlashAttribute("mensajeExito", "Medicamento guardado");
            return "redirect:/medicamentos";

        } catch (IllegalArgumentException e) {
            result.reject("error.guardar", e.getMessage());
            return "medicamentos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarMedicamento(
            @PathVariable Long id,
            Model model) {

        Medicamento medicamento =
                medicamentoService.obtenerPorId(id);

        dto dtoObj = new dto();

        dtoObj.setId(medicamento.getId());
        dtoObj.setNombre(medicamento.getNombre());
        dtoObj.setLaboratorio(
                medicamento.getLaboratorio().getNombre());
        dtoObj.setPrecio(medicamento.getPrecio());
        dtoObj.setStock(medicamento.getStock());
        dtoObj.setFechaVencimiento(
                medicamento.getFechaVencimiento());

        model.addAttribute("medicamento", dtoObj);

        return "medicamentos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedicamento(@PathVariable Long id) {

        medicamentoService.eliminar(id);

        return "redirect:/medicamentos";
    }
}