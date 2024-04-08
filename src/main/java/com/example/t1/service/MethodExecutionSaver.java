package com.example.t1.service;

import com.example.t1.model.MethodExecution;
import com.example.t1.model.enums.MethodType;
import com.example.t1.repository.MethodExecutionRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MethodExecutionSaver {

    private final MethodExecutionRepository methodExecutionRepository;

    @Autowired
    public MethodExecutionSaver(@Nonnull MethodExecutionRepository methodExecutionRepository) {
        this.methodExecutionRepository = methodExecutionRepository;
    }

    @Transactional
    public void save(String methodName,
                     Long timeTaken,
                     boolean isAsync) {
        MethodType methodType = MethodType.fromMethodName(methodName);
        MethodExecution methodExecution = new MethodExecution();
        methodExecution.setMethodType(methodType);
        methodExecution.setExecutionTime(timeTaken);
        methodExecution.setAsync(isAsync);

        methodExecutionRepository.save(methodExecution);
    }
}
