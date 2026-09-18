package com.proyecto.inventario.aspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginLogAspect {

    @Before("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void antes(JoinPoint joinPoint) {

        String usuario = obtenerUsuario(joinPoint);
        String fecha = obtenerFecha();

        System.out.println("Iniciando sesión");
        System.out.println("Usuario: " + usuario);
        System.out.println("Fecha y hora: " + fecha);
    }

    @AfterReturning(
        pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
        returning = "resultado"
    )
    public void sesionCorrecta(JoinPoint joinPoint, Object resultado) {

        Authentication autenticacion = (Authentication) resultado;

        System.out.println("Sesión iniciada correctamente");
        System.out.println("Usuario autenticado: " + autenticacion.getName());
    }

    @AfterThrowing(
        pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
        throwing = "excepcion"
    )
    public void sesionIncorrecta(
            JoinPoint joinPoint,
            AuthenticationException excepcion) {

        String usuario = obtenerUsuario(joinPoint);

        System.out.println("Usuario o contraseña incorrectos");
        System.out.println("Usuario que intentó ingresar: " + usuario);
    }

    @After("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void despues(JoinPoint joinPoint) {
        System.out.println("Finalizó inicio de sesión");
    }

    private String obtenerUsuario(JoinPoint joinPoint) {

        Object[] argumentos = joinPoint.getArgs();

        if (argumentos.length > 0 && argumentos[0] instanceof Authentication) {
            Authentication autenticacion = (Authentication) argumentos[0];
            return autenticacion.getName();
        }

        return "Usuario desconocido";
    }

    private String obtenerFecha() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return LocalDateTime.now().format(formato);
    }
}