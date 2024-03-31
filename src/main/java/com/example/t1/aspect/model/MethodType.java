package com.example.t1.aspect.model;

public enum MethodType {
    ADD_PLANT("addPlant"),
    GET_PLANT_BY_NAME("getPlantByName"),
    GET_PLANTS_BY_TYPE("getPlantsByType");


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
}
