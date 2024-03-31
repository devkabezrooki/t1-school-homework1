package com.example.t1.aspect.model;

import com.example.t1.aspect.service.MethodExecutionSaver;
import jakarta.annotation.Nonnull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Component
@Aspect
public class TrackTimeAsyncAspect {

    private final MethodExecutionSaver methodExecutionSaver;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    public TrackTimeAsyncAspect(@Nonnull MethodExecutionSaver methodExecutionSaver,
                                @Nonnull ThreadPoolTaskExecutor asyncTaskExecutor) {
        this.methodExecutionSaver = methodExecutionSaver;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Around("@annotation(TrackAsyncTime)")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Date startExecutionDate = new Date();
        final Object[] proceed = new Object[1];
        asyncTaskExecutor.submit(() -> {
            try {
                proceed[0] = joinPoint.proceed();
                long timeTaken = System.currentTimeMillis() - startTime;
                System.out.println(joinPoint.getSignature() + " выполнен асинхронно за " + timeTaken + " мс");

                String methodName = joinPoint.getSignature().getName();

                System.out.println(methodName + " выполнен за " + timeTaken + " мс");

                methodExecutionSaver.save(methodName, timeTaken, startExecutionDate, true);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return proceed[0];
    }
}
