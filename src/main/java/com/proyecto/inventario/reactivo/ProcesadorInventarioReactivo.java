package com.proyecto.inventario.reactivo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProcesadorInventarioReactivo {

    public Flux<MedicamentoReactivo> fuenteMedicamentos() {
        return Flux.just(
            new MedicamentoReactivo(1L, "Ibuprofeno", 1500.0, true),
            new MedicamentoReactivo(2L, "Paracetamol", 1200.0, false), 
            new MedicamentoReactivo(3L, "Aspirina", 2000.0, true),
            new MedicamentoReactivo(4L, "Amoxicilina", 4500.0, true)
        );
    }

    public Flux<String> procesarActivos() {
        return fuenteMedicamentos()
            .filter(MedicamentoReactivo::activo) 
            .map(m -> "Medicamento activo -> " + m.nombre() + " ($" + m.precio() + ")");
    }

    public Mono<Long> contarActivos() {
        return fuenteMedicamentos()
            .filter(MedicamentoReactivo::activo)
            .count();
    }
}