package com.example.t1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plant extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "plant_type", nullable = false)
    private PlantType type;
    @Column(name = "water_frequency", nullable = false)
    private int wateringFrequency;

}
