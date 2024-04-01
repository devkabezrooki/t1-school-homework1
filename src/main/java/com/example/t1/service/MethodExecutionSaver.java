package com.example.t1.service;

import com.example.t1.model.MethodExecution;
import com.example.t1.model.MethodType;
import com.example.t1.repository.MethodExecutionRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MethodExecutionSaver {

    private final MethodExecutionRepository methodExecutionRepository;

    @Autowired
    public MethodExecutionSaver(@Nonnull MethodExecutionRepository methodExecutionRepository) {
        this.methodExecutionRepository = methodExecutionRepository;
    }

    @Async
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
