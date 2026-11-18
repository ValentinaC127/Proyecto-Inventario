package com.proyecto.inventario.webflux;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
public class InventarioReactiveService {

    public Mono<String> bienvenidaInventario(String usuario) {
        String nombre = (usuario == null || usuario.isBlank()) ? "Personal Farmaceutico" : usuario.trim();
        return Mono.just("Bienvenido al modulo reactivo de inventario, " + nombre + ". Estado: Operativo.");
    }

    public Flux<String> flujoEventosStock() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .map(i -> "Evento de Stock #" + (i + 1) + ": Verificacion automatica de medicamentos activos.");
    }
}