package com.example.t1.repository;

import com.example.t1.model.Plant;
import com.example.t1.model.enums.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Optional<Plant> findByName(String name);
    List<Plant> findAllByType(PlantType type);

    List<Plant> findAllByWateringFrequencyGreaterThan(int frequency);
}
