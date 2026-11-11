package com.proyecto.inventario.reactivo;

import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;

@Component
public class ControlDemanda {

    private final ProcesadorInventarioReactivo procesador;

    public ControlDemanda(ProcesadorInventarioReactivo procesador) {
        this.procesador = procesador;
    }

    public void ejecutarDemanda() {
        procesador.fuenteMedicamentos().subscribe(new BaseSubscriber<MedicamentoReactivo>() {
            private int recibidos = 0;

            @Override
            protected void hookOnSubscribe(org.reactivestreams.Subscription subscription) {
                request(1); 
            }

            @Override
            protected void hookOnNext(MedicamentoReactivo value) {
                recibidos++;
                System.out.println("BaseSubscriber recibió: " + value.nombre() + " (Item #" + recibidos + ")");
                
                if (recibidos == 2) {
                    System.out.println("--> ¡Llegamos a 2! Cancelando demanda con cancel()");
                    cancel(); 
                } else {
                    request(1); 
                }
            }
        });
    }
}