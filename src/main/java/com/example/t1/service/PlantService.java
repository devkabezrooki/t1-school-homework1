package com.example.t1.service;

import com.example.t1.aspect.annotations.TrackAsyncTime;
import com.example.t1.aspect.annotations.TrackTime;
import com.example.t1.model.Plant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final Map<String, Plant> plants = new HashMap<>();

    @TrackTime
    public void addPlant(Plant plant) {
        plants.put(plant.getName(), plant);
    }

    @TrackTime
    public Plant getPlantByName(String name) {
        return plants.get(name);
    }

    @TrackAsyncTime
    public List<Plant> getPlantsByType(String type) {
        return plants.values().stream()
                .filter(p -> p.getType().equals(type))
                .collect(Collectors.toList());
    }
}
