package com.proyecto.inventario.webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
public class InventarioClienteService {

    private final WebClient cliente;

    public InventarioClienteService(WebClient.Builder builder, 
                                    @Value("${demo.fuente.url}") String urlBase) {
        this.cliente = builder.baseUrl(urlBase).build();
    }

    public Mono<String> consultarEstadoRemoto() {
        return cliente.get()
                .uri("/api/v1/reactive/fuente/estado")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(3))
                .map(valor -> "WebClient recibio respuesta remota: " + valor)
                .onErrorResume(e -> Mono.just("Fuente remota de inventario no disponible (Fallback activado)"));
    }
}
