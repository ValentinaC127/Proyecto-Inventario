package com.proyecto.inventario.reactivo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EjecutorReactivo implements CommandLineRunner {

    private final ProcesadorInventarioReactivo procesador;
    private final ControlDemanda controlDemanda;

    public EjecutorReactivo(ProcesadorInventarioReactivo procesador, ControlDemanda controlDemanda) {
        this.procesador = procesador;
        this.controlDemanda = controlDemanda;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n-----------------------------------------------------");
        System.out.println("---- DEMOSTRACION REACTIVA - INVENTARIO FARMACEUTICO ----");
        System.out.println("--------------------------------------------------------\n");

        System.out.println("--- 1. Procesamiento Activo (Filter, Map y Subscribe) ---");
        procesador.procesarActivos().subscribe(
            valor -> System.out.println("onNext: " + valor),
            error -> System.err.println("onError: " + error.getMessage()),
            () -> System.out.println("onComplete: Flujo finalizado con éxito.\n")
        );

        System.out.println("--- 2. Conteo Total (Mono) ---");
        procesador.contarActivos().subscribe(
            total -> System.out.println("Total de medicamentos activos (Mono<Long>): " + total + "\n")
        );

        System.out.println("--- 3. Control de Demanda (Backpressure) ---");
        controlDemanda.ejecutarDemanda();

        System.out.println("\n------------------------------------------");
        System.out.println("--- FIN DE LA DEMOSTRACIÓN REACTIVA ----");
        System.out.println("--------------------------------------------\n");
    }
}