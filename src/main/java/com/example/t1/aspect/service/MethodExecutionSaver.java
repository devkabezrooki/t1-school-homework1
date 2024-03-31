package com.example.t1.aspect.service;

import com.example.t1.aspect.model.MethodExecution;
import com.example.t1.aspect.model.MethodType;
import com.example.t1.aspect.repository.MethodExecutionRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class MethodExecutionSaver {

    private final MethodExecutionRepository methodExecutionRepository;

    @Autowired
    public MethodExecutionSaver(@Nonnull MethodExecutionRepository methodExecutionRepository) {
        this.methodExecutionRepository = methodExecutionRepository;
    }

    @Transactional
    public void save(String methodName,
                     Long timeTaken,
                     Date startExecutionDate,
                     boolean isAsync) {
        MethodType methodType = MethodType.fromMethodName(methodName);
        MethodExecution methodExecution = new MethodExecution();
        methodExecution.setMethodType(methodType);
        methodExecution.setExecutionTime(timeTaken);
        methodExecution.setExecutionDate(startExecutionDate);
        methodExecution.setAsync(isAsync);

        methodExecutionRepository.save(methodExecution);
    }
}
