package com.proyecto.inventario.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginLogAspect {

    @Before("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void antes(JoinPoint joinPoint) {
        System.out.println("Iniciando sesión");
    }

    @AfterReturning(
        pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
        returning = "resultado"
    )
    public void sesionCorrecta(JoinPoint joinPoint, Object resultado) {
        System.out.println("Sesión iniciada correctamente");
    }

    @AfterThrowing(
        pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
        throwing = "excepcion"
    )
    public void sesionIncorrecta(
            JoinPoint joinPoint,
            AuthenticationException excepcion) {

        System.out.println("Usuario o contraseña incorrectos");
    }

    @After("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void despues(JoinPoint joinPoint) {
        System.out.println("Finalizó inicio de sesión");
    }
}