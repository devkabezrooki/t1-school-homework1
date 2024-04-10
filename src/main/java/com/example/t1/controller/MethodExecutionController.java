package com.example.t1.controller;

import com.example.t1.model.dto.MethodAverageExecutionTimeDto;
import com.example.t1.model.dto.MethodExecutionCountDto;
import com.example.t1.model.enums.MethodType;
import com.example.t1.model.enums.SortOrder;
import com.example.t1.service.MethodExecutionService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/execution/statistics")
public class MethodExecutionController {

    private final MethodExecutionService methodExecutionService;

    @Autowired
    public MethodExecutionController(@Nonnull MethodExecutionService methodExecutionService) {
        this.methodExecutionService = methodExecutionService;
    }

    @GetMapping("/averageTime/list")
    public ResponseEntity<List<MethodAverageExecutionTimeDto>> getAverageExecutionTime(
            @RequestParam(value = "async", required = false) Boolean isAsync) {
        if (isAsync != null) {
            return ResponseEntity.ok(methodExecutionService.getMethodsAverageExecutionTimeByAsync(isAsync));
        }  else {
            return ResponseEntity.ok(methodExecutionService.getMethodsAverageExecutionTime());
        }
    }

    @GetMapping("/averageTime")
    public ResponseEntity<MethodAverageExecutionTimeDto> getAverageExecutionTime(
            @RequestParam(value = "methodType", required = false) MethodType methodType,
            @RequestParam(value = "async", required = false) Boolean isAsync) {
        if (methodType != null && isAsync != null && methodType.isAsync() != isAsync) {
            String message = "Метод " + methodType + " не является " + (isAsync ? "асинхронным" : "синхронным");
            throw new IllegalArgumentException(message);
        }
        if (methodType != null) {
            return ResponseEntity.ok(methodExecutionService.getMethodAverageExecutionTimeByMethodType(methodType));
        } else if (isAsync != null) {
            return ResponseEntity.ok(methodExecutionService.getAllMethodsAverageExecutionTimeByAsync(isAsync));
        } else {
            return ResponseEntity.ok(methodExecutionService.getAllMethodsAverageExecutionTime());
        }
    }

    @GetMapping("/averageTime/max")
    public ResponseEntity<MethodAverageExecutionTimeDto> getMaxAverageExecutionTime() {
        return ResponseEntity.ok(methodExecutionService.getMaxOrMinAverageExecutionTime(SortOrder.ASC));
    }

    @GetMapping("/averageTime/min")
    public ResponseEntity<MethodAverageExecutionTimeDto> getMinAverageExecutionTime() {
        return ResponseEntity.ok(methodExecutionService.getMaxOrMinAverageExecutionTime(SortOrder.DESC));
    }

    @GetMapping("/count")
    public ResponseEntity<MethodExecutionCountDto> getExecutionsCount(
            @RequestParam(value = "methodType", required = false) MethodType methodType) {
        if (methodType != null) {
            return ResponseEntity.ok(methodExecutionService.getMethodExecutionsCountByMethodType(methodType));
        } else {
            return ResponseEntity.ok(methodExecutionService.getMethodExecutionsCount());
        }
    }

}
