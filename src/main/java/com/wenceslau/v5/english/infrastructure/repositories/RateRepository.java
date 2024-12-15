package com.wenceslau.v5.english.infrastructure.repositories;

import java.util.Map;

public class RateRepository {

    private final Map<String, Double> rates = Map.of(
            "CAR", 4.0,
            "MOTORCYCLE", 2.0,
            "TRUCK", 6.0,
            "BICYCLE", 1.0
    );

    public double getRateByType(String vehicleType) {
        return  rates.get(vehicleType.toUpperCase());
    }

}
