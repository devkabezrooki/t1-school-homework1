package com.example.t1.repository;

import com.example.t1.model.Plant;
import com.example.t1.model.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    public Plant findByName(String name);
    public List<Plant> findAllByType(PlantType type);

    public List<Plant> findAllByWateringFrequencyGreaterThan(int frequency);
}
