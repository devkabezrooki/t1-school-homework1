package com.example.t1.model.enums;

import java.util.Set;

public enum MethodType {
    ADD_PLANT("addPlant"),
    GET_PLANT_BY_NAME("getPlantByName"),
    GET_PLANTS_BY_TYPE("getPlantsByType"),
    PLANT_WATERING("waterPlant"),
    GET_ALL_PLANTS_FOR_WATERING("getAllPlantsThatRequireWatering"),
    WATER_PLANTS("waterPlants");

    public static Set<MethodType> ASYNC_METHODS = Set.of(GET_ALL_PLANTS_FOR_WATERING, WATER_PLANTS);


    private final String methodName;
    MethodType(String methodName) {
        this.methodName = methodName;
    }

    public String methodName() {
        return methodName;
    }

    public static MethodType fromMethodName(String methodName) {
        for (MethodType t : MethodType.values()) {
            if (t.methodName().equals(methodName)) {
                return t;
            }
        }
        throw new IllegalArgumentException(methodName);
    }

    public boolean isAsync() {
        return ASYNC_METHODS.contains(this);
    }
}
