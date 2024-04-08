package com.example.t1.service;

import com.example.t1.model.enums.MethodType;
import com.example.t1.model.enums.SortOrder;
import com.example.t1.model.dto.MethodAverageExecutionTimeDto;
import com.example.t1.model.dto.MethodExecutionCountDto;
import com.example.t1.repository.MethodExecutionRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MethodExecutionService {

    private final MethodExecutionRepository methodExecutionRepository;

    @Autowired
    public MethodExecutionService(@Nonnull MethodExecutionRepository methodExecutionRepository) {
        this.methodExecutionRepository = methodExecutionRepository;
    }

    public List<MethodAverageExecutionTimeDto> getMethodsAverageExecutionTime() {
        return methodExecutionRepository.getMethodsAverageExecutionTime();
    }

    public List<MethodAverageExecutionTimeDto> getMethodsAverageExecutionTimeByAsync(boolean isAsync) {
        return methodExecutionRepository.getMethodsAverageExecutionTimeByAsync(isAsync);
    }

    public MethodAverageExecutionTimeDto getMethodAverageExecutionTimeByMethodType(MethodType methodType) {
        return methodExecutionRepository.getMethodAverageExecutionTimeByMethodType(methodType);
    }

    public MethodAverageExecutionTimeDto getAllMethodsAverageExecutionTime() {
        return methodExecutionRepository.getAllMethodsAverageExecutionTime();
    }

    public MethodAverageExecutionTimeDto getAllMethodsAverageExecutionTimeByAsync(boolean isAsync) {
        return methodExecutionRepository.getAllMethodsAverageExecutionTimeByAsync(isAsync);
    }

    public MethodExecutionCountDto getMethodExecutionsCount() {
        return methodExecutionRepository.getMethodExecutionsCount();
    }

    public MethodExecutionCountDto getMethodExecutionsCountByMethodType(MethodType methodType) {
        return methodExecutionRepository.getMethodExecutionsCountByMethodType(methodType);
    }

    public MethodAverageExecutionTimeDto getMaxOrMinAverageExecutionTime(SortOrder order) {
        if (order == SortOrder.ASC) {
            return methodExecutionRepository.getMethodsAverageExecutionTime().stream()
                    .max(Comparator.comparing(MethodAverageExecutionTimeDto::getAverageTime))
                    .orElseThrow();
        } else {
            return methodExecutionRepository.getMethodsAverageExecutionTime().stream()
                    .min(Comparator.comparing(MethodAverageExecutionTimeDto::getAverageTime))
                    .orElseThrow();
        }
    }
}
