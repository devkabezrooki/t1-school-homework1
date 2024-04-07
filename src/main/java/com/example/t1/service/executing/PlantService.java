package com.example.t1.service.executing;

import com.example.t1.aspect.annotations.TrackAsyncTime;
import com.example.t1.aspect.annotations.TrackTime;
import com.example.t1.model.Plant;
import com.example.t1.model.PlantType;
import com.example.t1.model.PlantWatering;
import com.example.t1.repository.PlantRepository;
import com.example.t1.repository.PlantWateringRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantWateringRepository plantWateringRepository;

    @Autowired
    public PlantService(@Nonnull PlantRepository plantRepository,
                        @Nonnull PlantWateringRepository plantWateringRepository) {
        this.plantRepository = plantRepository;
        this.plantWateringRepository = plantWateringRepository;
    }

    @TrackTime
    @Transactional
    public void addPlant(Plant plant) {
        plantRepository.save(plant);
    }

    @TrackTime
    @Transactional
    public Plant getPlantByName(String name) {
        return plantRepository.findByName(name);
    }

    @TrackAsyncTime
    @Transactional
    public List<Plant> getPlantsByType(PlantType type) {
        return plantRepository.findAllByType(type);
    }

    @TrackAsyncTime
    @Transactional
    public void waterPlant(Plant plant) {
        PlantWatering plantWatering = new PlantWatering(plant);
        plantWateringRepository.save(plantWatering);
    }

    @TrackAsyncTime
    @Transactional
    public List<Plant> getAllPlantsThatRequireWatering() {
        return plantRepository.findAllByWateringFrequencyGreaterThan(0).stream()
                .filter(p -> {
                    PlantWatering lastWatering = plantWateringRepository.findTopByPlantOrderByWateringTimeDesc(p);
                    if (lastWatering == null) {
                        return true;
                    } else {
                        Duration duration = Duration.between(
                                lastWatering.getWateringTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        );
                        return duration.toHours() >= p.getWateringFrequency();
                    }
                })
                .collect(Collectors.toList());
    }

    @TrackAsyncTime
    @Transactional
    public void waterAllPlantsThatNeeded() {
        getAllPlantsThatRequireWatering().forEach(p -> waterPlant(p));
    }
}
