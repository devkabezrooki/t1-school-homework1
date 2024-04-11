package com.example.t1.aspect;

import com.example.t1.model.dto.MethodExecutionDto;
import com.example.t1.model.enums.MethodType;
import com.example.t1.service.MethodExecutionSaver;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1) //чтобы не мешать работе @Transactional
@Slf4j
public class TrackTimeAspect {

    private final MethodExecutionSaver methodExecutionSaver;

    @Autowired
    public TrackTimeAspect(@Nonnull MethodExecutionSaver methodExecutionSaver) {
        this.methodExecutionSaver = methodExecutionSaver;
    }

    @Pointcut("@annotation(com.example.t1.aspect.annotations.TrackTime)")
    public void trackTimePoincut(){};

    @Pointcut("@annotation(com.example.t1.aspect.annotations.TrackAsyncTime)")
    public void trackAsyncTimePointcut(){};

    @Around("trackTimePoincut() || trackAsyncTimePointcut()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        try {
            Object proceed = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            MethodType methodType = MethodType.fromMethodName(methodName);
            boolean isAsync = methodType.isAsync();

            log.info("метод {} выполнен {}за {}  мс", methodName, isAsync ? "асинхронно " : "", timeTaken);

            MethodExecutionDto executionDto = new MethodExecutionDto(methodType, timeTaken, isAsync);
            methodExecutionSaver.save(executionDto);
            return proceed;
        } catch (Throwable e) {
            log.error("В процессе выполнения метода {} произошла ошибка: ", methodName, e);
            return null;
        }
    }

}
