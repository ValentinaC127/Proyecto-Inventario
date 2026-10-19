package com.proyecto.inventario.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        int codigo = 500;
        if (status != null) {
            codigo = Integer.parseInt(status.toString());
        }

        String mensaje = (codigo == 404) 
            ? "La página o recurso que buscas no existe." 
            : "Ocurrió un error interno en el servidor.";

        model.addAttribute("codigo", codigo);
        model.addAttribute("mensaje", mensaje);
        
        return "medicamentos/error";
    }
}