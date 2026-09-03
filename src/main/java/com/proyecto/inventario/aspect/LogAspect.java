package com.proyecto.inventario.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.proyecto.inventario.service.*.*(..))")
    public void antes(JoinPoint joinPoint) {
        System.out.println("Iniciando: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.proyecto.inventario.service.*.*(..))")
    public void despues(JoinPoint joinPoint) {
        System.out.println("Finalizo: " + joinPoint.getSignature().getName());
    }
}