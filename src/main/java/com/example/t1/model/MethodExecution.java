package com.example.t1.model;

import com.example.t1.model.enums.MethodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "method_executions")
@Getter
@Setter
public class MethodExecution extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private MethodType methodType;

    @Column(name = "execution_time", nullable = false)
    private long executionTime;

    @Column(name = "is_async")
    private boolean isAsync = false;
}
