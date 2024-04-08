package com.example.t1.aspect;

import com.example.t1.service.tracking.MethodExecutionSaver;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
@Slf4j
public class TrackTimeAspect {

    private final MethodExecutionSaver methodExecutionSaver;

    @Autowired
    public TrackTimeAspect(@Nonnull MethodExecutionSaver methodExecutionSaver) {
        this.methodExecutionSaver = methodExecutionSaver;
    }

    @Around("@annotation(com.example.t1.aspect.annotations.TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();

        log.info("метод {} выполнен за {}  мс", methodName, timeTaken);

        methodExecutionSaver.save(methodName, timeTaken, false);

        return proceed;
    }

}
