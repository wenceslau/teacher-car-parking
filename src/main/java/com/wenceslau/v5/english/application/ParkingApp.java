package com.wenceslau.v5.english.application;

import com.wenceslau.v5.english.domain.Vehicle;
import com.wenceslau.v5.english.domain.VehicleParking;
import com.wenceslau.v5.english.domain.VehicleRegistration;
import com.wenceslau.v5.english.domain.VehicleType;
import com.wenceslau.v5.english.infrastructure.repositories.ParkingRepository;
import com.wenceslau.v5.english.infrastructure.repositories.RateRepository;

public class ParkingApp {

    private final VehicleParking vehicleParking;
    private final RateRepository rateRepository;
    private final ParkingRepository parkingRepository;

    public ParkingApp(VehicleParking vehicleParking, RateRepository rateRepository, ParkingRepository parkingRepository) {
        this.vehicleParking = vehicleParking;
        this.rateRepository = rateRepository;
        this.parkingRepository = parkingRepository;
    }

    public VehicleRegistration registerLicensePlate(String licensePlate, String vehicleType) {

        // Convert the string to the VehicleType enum
        VehicleType type = VehicleType.from(vehicleType);

        Vehicle vehicle = type.getVehicle();
        vehicle.setLicensePlate(licensePlate);

        double rete = rateRepository.getRateByType(vehicleType);
        vehicle.setRate(rete);

        var vehicleRegistration = this.vehicleParking.registerPlate(vehicle);

        parkingRepository.save(vehicleRegistration);

        return vehicleRegistration;
    }

}
