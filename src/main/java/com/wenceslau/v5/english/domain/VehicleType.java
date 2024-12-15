package com.wenceslau.v5.english.domain;

import com.wenceslau.v5.english.domain.vehicles.Bicycle;
import com.wenceslau.v5.english.domain.vehicles.Car;
import com.wenceslau.v5.english.domain.vehicles.Motorcycle;
import com.wenceslau.v5.english.domain.vehicles.Truck;

import java.util.Arrays;

public enum VehicleType {
    CAR,
    MOTORCYCLE,
    TRUCK,
    BICYCLE;

    public Vehicle getVehicle() {
        return switch (this) {
            case CAR -> new Car();
            case MOTORCYCLE -> new Motorcycle();
            case TRUCK -> new Truck();
            case BICYCLE -> new Bicycle();
        };
    }

    public static VehicleType from(String vehicleType) {
        try {
            return VehicleType.valueOf(vehicleType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle type. Types available: " + Arrays.toString(VehicleType.values()));
        }
    }

}
