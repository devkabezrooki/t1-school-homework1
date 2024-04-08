package com.example.t1.model.dto;

import com.example.t1.model.enums.MethodType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MethodAverageExecutionTimeDto {

    @JsonProperty("Метод")
    private MethodType methodType;

    @JsonProperty("Среднее время выполнения")
    @NonNull
    private Double averageTime;
}
