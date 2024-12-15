package com.wenceslau.v5.english.infrastructure.repositories;

import com.wenceslau.v5.english.domain.VehicleRegistration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {

    private final Map<String, VehicleRegistration> registrationMap = new HashMap<>();

    public void save(VehicleRegistration veiculoRegistro) {
        var key = veiculoRegistro.getVehicle().getLicensePlate() + veiculoRegistro.getCheckIn();
        registrationMap.put(key, veiculoRegistro);
    }

    public List<VehicleRegistration> findAll() {
        return List.copyOf(registrationMap.values());
    }
}
