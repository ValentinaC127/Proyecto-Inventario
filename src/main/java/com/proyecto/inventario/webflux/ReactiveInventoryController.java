package com.proyecto.inventario.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive")
public class ReactiveInventoryController {

    private final InventarioReactiveService reactiveService;
    private final InventarioClienteService clienteService;

    public ReactiveInventoryController(InventarioReactiveService reactiveService, 
                                       InventarioClienteService clienteService) {
        this.reactiveService = reactiveService;
        this.clienteService = clienteService;
    }

    @GetMapping("/bienvenida/{usuario}")
    public Mono<String> bienvenida(@PathVariable String usuario) {
        return reactiveService.bienvenidaInventario(usuario);
    }

    @GetMapping(value = "/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> eventosStock() {
        return reactiveService.flujoEventosStock();
    }

    @GetMapping("/estado-remoto")
    public Mono<String> estadoRemoto() {
        return clienteService.consultarEstadoRemoto();
    }

    @GetMapping("/fuente/estado")
    public Mono<String> fuenteEstado() {
        return Mono.just("SERVIDOR_CENTRAL_ACTIVO");
    }
}