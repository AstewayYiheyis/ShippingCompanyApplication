package com.example.shippingcompanyapplication.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example..*.*(..))")
    public void logBefore(JoinPoint joinPoint){
        logger.info("Method about to be executed: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning("execution(* com.example..*.*(..))")
    public void logAfterReturning(JoinPoint joinPoint){
        logger.info("Method completed: " + joinPoint.getSignature().toShortString());
    }
}
