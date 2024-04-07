package com.example.t1.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
