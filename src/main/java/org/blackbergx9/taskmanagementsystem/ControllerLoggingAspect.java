package org.blackbergx9.taskmanagementsystem;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Aspect // AOP
@Profile("dev")
public class ControllerLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    @Around("execution(* org.blackbergx9.taskmanagementsystem.controller.*.*(..))")
    public Object logRequestTime(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("{} Execution Started",  joinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        logger.info("{} executed in {}ms", joinPoint.getSignature(), duration);

        return proceed;

    }
}
