package com.example.t1.service;

import com.example.t1.model.MethodExecution;
import com.example.t1.model.dto.MethodExecutionDto;
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
    public void save(MethodExecutionDto executionDto) {
        MethodExecution methodExecution = new MethodExecution();
        methodExecution.setMethodType(executionDto.getMethodType());
        methodExecution.setExecutionTime(executionDto.getExecutionTime());
        methodExecution.setAsync(executionDto.getMethodType().isAsync());

        methodExecutionRepository.save(methodExecution);
    }
}
