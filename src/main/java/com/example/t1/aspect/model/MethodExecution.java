package com.example.t1.aspect.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "method_executions")
@Getter
@Setter
public class MethodExecution {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private MethodType methodType;

    @Column(name = "execution_time", nullable = false)
    private long executionTime;

    @Column(name = "execution_date", nullable = false)
    private Date executionDate;

    @Column(name = "is_async")
    private boolean isAsync = false;
}
