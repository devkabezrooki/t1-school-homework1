package com.example.t1.model.dto;

import com.example.t1.model.enums.MethodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MethodExecutionDto {

    private MethodType methodType;
    private long executionTime;
    private boolean isAsync;
}
