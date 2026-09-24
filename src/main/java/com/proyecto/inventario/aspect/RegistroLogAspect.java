package com.proyecto.inventario.aspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistroLogAspect {

    private static final String POINTCUT_REGISTRO = "execution(* com.proyecto.inventario.controller.RegistroController.registrarUsuario(..))";

    @Before(POINTCUT_REGISTRO)
    public void antesDeRegistrar(JoinPoint joinPoint) {
        String usuario = obtenerUsuario(joinPoint);
        String fecha = obtenerFecha();

        System.out.println("Iniciando registro de usuario");
        System.out.println("Usuario a registrar: " + usuario);
        System.out.println("Fecha y hora: " + fecha);
    }

    @AfterReturning(
        pointcut = POINTCUT_REGISTRO,
        returning = "resultado"
    )
    public void registroCorrecto(JoinPoint joinPoint, Object resultado) {
        String usuario = obtenerUsuario(joinPoint);

        System.out.println("Registro completado con éxito");
        System.out.println("Usuario guardado: " + usuario);
    }

    @AfterThrowing(
        pointcut = POINTCUT_REGISTRO,
        throwing = "excepcion"
    )
    public void registroIncorrecto(JoinPoint joinPoint, Exception excepcion) {
        String usuario = obtenerUsuario(joinPoint);

        System.out.println("Error en el registro de usuario");
        System.out.println("Usuario que intentó registrarse: " + usuario);
        System.out.println("Motivo: " + excepcion.getMessage());
    }

    @After(POINTCUT_REGISTRO)
    public void despuesDeRegistrar(JoinPoint joinPoint) {
        System.out.println("Finalizó el proceso de registro");
    }

    private String obtenerUsuario(JoinPoint joinPoint) {
        Object[] argumentos = joinPoint.getArgs();

        for (Object arg : argumentos) {
            if (arg != null && arg instanceof String) {
                return (String) arg;
            }
        }

        if (argumentos.length > 0 && argumentos[0] != null) {
            return argumentos[0].toString();
        }

        return "Desconocido";
    }

    private String obtenerFecha() {
        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return LocalDateTime.now().format(formato);
    }
}