package com.example.t1.model.dto;

import com.example.t1.model.enums.MethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MethodExecutionDto {

    private MethodType methodType;
    private long executionTime;
    private boolean isAsync;
}
