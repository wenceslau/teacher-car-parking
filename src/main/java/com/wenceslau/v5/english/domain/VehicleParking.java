package com.wenceslau.v5.english.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleParking {

    private final int capacity;
    private final List<VehicleRegistration> registrations;

    public VehicleParking(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        this.capacity = capacity;
        this.registrations = new ArrayList<>();
    }

    public VehicleRegistration registerPlate(Vehicle vehicle) {

        if (vehicle == null){
            throw new IllegalArgumentException("Vehicle is required");
        }

        var optional = searchRegistration(vehicle);

        return optional
                .map(this::checkOutVehicle)
                .orElseGet(() -> checkInVehicle(vehicle));

        //if (optional.isEmpty()) {
        //    return checkInVehicle(vehicle);
        //}else {
        //    return checkOutVehicle(optional.get());
        //}
    }

    private Optional<VehicleRegistration> searchRegistration(Vehicle vehicle) {
        return registrations.stream()
                .filter(r -> r.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate()))
                .filter(r -> r.getCheckOut() == null)
                .findFirst();
    }

    private VehicleRegistration checkInVehicle(Vehicle vehicle) {
        int count = registrations.stream()
                .filter(r -> r.getCheckOut() == null)
                .mapToInt(r -> 1).sum();

        if (count == capacity) {
            throw new IllegalStateException("Parking lot is full");
        }

        var registration = new VehicleRegistration(vehicle, LocalDateTime.now());
        registrations.add(registration);
        return registration;
    }

    private VehicleRegistration checkOutVehicle(VehicleRegistration registration) {
        registration.setCheckOut(LocalDateTime.now());
        return registration;
    }

    public List<VehicleRegistration> getRegistrations() {
        return registrations;
    }

    public int getCapacity() {
        return capacity;
    }
}
