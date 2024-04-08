package com.example.t1.service;

import com.example.t1.aspect.annotations.TrackAsyncTime;
import com.example.t1.aspect.annotations.TrackTime;
import com.example.t1.model.Plant;
import com.example.t1.model.enums.PlantType;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    @TrackTime
    @Transactional
    public List<Plant> getPlantsByType(PlantType type) {
        return plantRepository.findAllByType(type);
    }

    @TrackTime
    @Transactional
    public void waterPlant(Plant plant) {
        PlantWatering plantWatering = new PlantWatering(plant);
        plantWateringRepository.save(plantWatering);
    }

    @TrackAsyncTime
    @Transactional
    public List<Plant> getAllPlantsThatRequireWatering() {
        List<Plant> allPlantsWithWatering = plantRepository.findAllByWateringFrequencyGreaterThan(0);
        List<CompletableFuture<Plant>> allFutures = new LinkedList<>();

        allPlantsWithWatering.forEach(p -> {
            CompletableFuture<Plant> completableFuture = CompletableFuture.supplyAsync(() -> {
                        PlantWatering lastWatering = plantWateringRepository.findTopByPlantOrderByWateringTimeDesc(p);
                        if (lastWatering == null) {
                            return p;
                        }
                        Duration duration = Duration.between(
                                lastWatering.getWateringTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        );
                        return duration.toHours() >= lastWatering.getPlant().getWateringFrequency() ? p : null;
                    }
            );
            allFutures.add(completableFuture);
        });

        CompletableFuture<List<Plant>> combinedFuture = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[allFutures.size()]))
                .thenApply(f -> allFutures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));

        return combinedFuture.join();
    }

    @TrackAsyncTime
    @Transactional
    public void waterAllPlantsThatNeeded() {
        List<Plant> allPlantsThatRequireWatering = getAllPlantsThatRequireWatering();
        List<CompletableFuture<Void>> allFutures = new LinkedList<>();

        allPlantsThatRequireWatering.forEach(p -> {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                        waterPlant(p);
                        return null;
                    }
            );
            allFutures.add(completableFuture);
        });

        CompletableFuture<Void> allOf = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[allFutures.size()]));

        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
