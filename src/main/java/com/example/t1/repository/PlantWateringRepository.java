package com.example.t1.repository;

import com.example.t1.model.Plant;
import com.example.t1.model.PlantWatering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantWateringRepository extends JpaRepository<PlantWatering, Long> {

    PlantWatering findTopByPlantOrderByWateringTimeDesc(Plant plant);

}
