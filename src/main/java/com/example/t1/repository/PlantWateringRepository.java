package com.example.t1.repository;

import com.example.t1.model.Plant;
import com.example.t1.model.PlantWatering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantWateringRepository extends JpaRepository<PlantWatering, Long> {

    Optional<PlantWatering> findTopByPlantOrderByWateringTimeDesc(Plant plant);

}
