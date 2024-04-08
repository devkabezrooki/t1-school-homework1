package com.example.t1.aspect;

import com.example.t1.model.dto.MethodExecutionDto;
import com.example.t1.model.enums.MethodType;
import com.example.t1.service.MethodExecutionSaver;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Slf4j
public class TrackTimeAspect {

    private final MethodExecutionSaver methodExecutionSaver;

    @Autowired
    public TrackTimeAspect(@Nonnull MethodExecutionSaver methodExecutionSaver) {
        this.methodExecutionSaver = methodExecutionSaver;
    }

    @Around("@annotation(com.example.t1.aspect.annotations.TrackTime) " +
            "|| @annotation(com.example.t1.aspect.annotations.TrackAsyncTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            String methodName = joinPoint.getSignature().getName();
            MethodType methodType = MethodType.fromMethodName(methodName);
            boolean isAsync = methodType.isAsync();

            log.info("метод {} выполнен {}за {}  мс", methodName, isAsync ? "асинхронно" : "", timeTaken);

            MethodExecutionDto executionDto = new MethodExecutionDto().setMethodType(MethodType.fromMethodName(methodName))
                    .setExecutionTime(timeTaken).setAsync(isAsync);
            CompletableFuture.runAsync(() -> methodExecutionSaver.save(executionDto));
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}
