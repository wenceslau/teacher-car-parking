package com.wenceslau.v5.english.application;

import com.wenceslau.v5.english.domain.VehicleParking;
import com.wenceslau.v5.english.domain.VehicleRegistration;

import java.util.List;

public class ReportApp {

    private final VehicleParking vehicleParking;

    public ReportApp(VehicleParking vehicleParking) {
        this.vehicleParking = vehicleParking;
    }

    public List<VehicleRegistration> vehiclesParked() {
        return vehicleParking.getRegistrations().stream()
                .filter(r -> r.getCheckOut() == null)
                .toList();
    }

    public List<VehicleRegistration> checkoutLog() {
        return vehicleParking.getRegistrations().stream()
                .filter(r -> r.getCheckOut() != null)
                .toList();
    }

}
