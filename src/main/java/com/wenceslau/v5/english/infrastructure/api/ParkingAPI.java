package com.wenceslau.v5.english.infrastructure.api;

import com.wenceslau.v5.english.application.ParkingApp;
import com.wenceslau.v5.english.application.ReportApp;
import com.wenceslau.v5.english.domain.VehicleParking;
import com.wenceslau.v5.english.infrastructure.api.records.Presentation;
import com.wenceslau.v5.english.infrastructure.repositories.ParkingRepository;
import com.wenceslau.v5.english.infrastructure.repositories.RateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking")
public class ParkingAPI {

    private final RateRepository rateRepository = new RateRepository();
    private final ParkingRepository parkingRepository = new ParkingRepository();
    private ParkingApp parkingApp;
    private ReportApp reportApp;

    @PostMapping("/create/{capacity}")
    public ResponseEntity<?> createParking(@PathVariable int capacity) {

        try {
            VehicleParking vehicleParking = new VehicleParking(capacity);
            parkingApp = new ParkingApp(vehicleParking, rateRepository, parkingRepository);
            reportApp = new ReportApp(vehicleParking);

            return ResponseEntity
                    .ok()
                    .body(Presentation.buildCreateResponseString(capacity));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/register/{licensePlate}/{vehicleType}")
    public ResponseEntity<?> register(@PathVariable String licensePlate, @PathVariable String vehicleType) {

        try {
            var vehicleRegistration = parkingApp.registerLicensePlate(licensePlate, vehicleType);
            var vehiclesParked = reportApp.vehiclesParked();
            var checkoutLog = reportApp.checkoutLog();

            return ResponseEntity
                    .ok()
                    .body(Presentation.buildRegisterResponseString(vehicleRegistration, vehiclesParked, checkoutLog));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/report")
    public ResponseEntity<?> report() {

        try {
            var vehiclesParked = reportApp.vehiclesParked();
            var checkoutLog = reportApp.checkoutLog();

            return ResponseEntity
                    .ok()
                    .body(Presentation.buildReportResponseString(vehiclesParked, checkoutLog));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}

