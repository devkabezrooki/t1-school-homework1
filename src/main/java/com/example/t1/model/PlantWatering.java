package com.example.t1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "plant_waterings")
@Getter
@Setter
@NoArgsConstructor
public class PlantWatering extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @Column(name = "watering_time", nullable = false)
    private Date wateringTime;

    public PlantWatering(Plant plant) {
        this.plant = plant;
        this.wateringTime = new Date();
    }
}
