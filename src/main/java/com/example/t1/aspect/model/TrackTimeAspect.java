package com.example.t1.aspect.model;

import com.example.t1.aspect.service.MethodExecutionSaver;
import jakarta.annotation.Nonnull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class TrackTimeAspect {

    private final MethodExecutionSaver methodExecutionSaver;

    @Autowired
    public TrackTimeAspect(@Nonnull MethodExecutionSaver methodExecutionSaver) {
        this.methodExecutionSaver = methodExecutionSaver;
    }

    @Around("@annotation(TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Date startExecutionDate = new Date();
        Object proceed = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();

        System.out.println(methodName + " выполнен за " + timeTaken + " мс");

        methodExecutionSaver.save(methodName, timeTaken, startExecutionDate, false);

        return proceed;
    }

}
