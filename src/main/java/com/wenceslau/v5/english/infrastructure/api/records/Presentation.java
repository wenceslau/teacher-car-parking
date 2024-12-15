package com.wenceslau.v5.english.infrastructure.api.records;

import com.wenceslau.v5.english.domain.VehicleRegistration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Presentation {

    /* Presentation using String Responses*/

    public static String buildCreateResponseString(int capacity) {
        return "Parking created with capacity: %d%n".formatted(capacity);
    }

    public static String buildRegisterResponseString(VehicleRegistration vehicleRegistration,
                                                     List<VehicleRegistration> vehiclesParked,
                                                     List<VehicleRegistration> checkoutReport) {
        String message;
        if (vehicleRegistration.getCheckOut() == null) {
            message = "Entrance of the vehicle with plate: %s performed.%n"
                    .formatted(vehicleRegistration.getVehicle().getLicensePlate());
        } else {
            message = "Exit of the vehicle with plate: %s. Time: %d minutes. Value to be charged: $ %.2f%n"
                    .formatted(vehicleRegistration.getVehicle().getLicensePlate(), vehicleRegistration.getDuration().toMinutes(),
                            vehicleRegistration.getAmount());
        }

        message += "\n";

        message += buildReportResponseString(vehiclesParked, checkoutReport);

        return message;
    }

    public static String buildReportResponseString(List<VehicleRegistration> vehiclesParked, List<VehicleRegistration> checkoutReport) {
        var message = new StringBuilder();

        message.append("_______________________________________________________________________________\n");

        message.append("VEHICLES PARKED: \n");

        for (VehicleRegistration registration : vehiclesParked) {
            var checkIn = registration.getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            message.append("\tLicense Plate %s - check-in date: %s %n".formatted(registration.getVehicle().getLicensePlate(), checkIn));
        }

        message.append("\n");

        message.append("EXIT RECORDS: \n");

        for (VehicleRegistration registration : checkoutReport) {
            var licensePlate = registration.getVehicle().getLicensePlate();
            var minutes = registration.getDuration().toMinutes();
            var amount = registration.getAmount();
            message.append("\tLicense Plate %s - Time: %d minutes - Value charged: $ %.2f%n"
                    .formatted(licensePlate, minutes, amount));
        }

        message.append("_______________________________________________________________________________\n");

        return message.toString();

    }

    /* Presentation using Objects Responses*/

    public static CreateResponse buildCreateResponse(int capacity) {
        return new CreateResponse(capacity, LocalDateTime.now());
    }

    public static RegisterResponse buildRegisterResponse(VehicleRegistration vehicleRegistration,
                                                         List<VehicleRegistration> vehiclesParked,
                                                         List<VehicleRegistration> checkoutReport) {

        var reportResponse = buildReportResponse(vehiclesParked, checkoutReport);

        var type = "CheckIn";
        Long duration = null;
        if (vehicleRegistration.getCheckOut() != null) {
            type = "CheckOut";
            duration = vehicleRegistration.getDuration().toMinutes();
        }
        String className = vehicleRegistration.getVehicle().getClass().getSimpleName().toUpperCase();
        return new RegisterResponse(
                type,
                vehicleRegistration.getVehicle().getLicensePlate(),
                className,
                vehicleRegistration.getVehicle().getRate(),
                vehicleRegistration.getCheckIn(),
                vehicleRegistration.getCheckOut(),
                duration,
                vehicleRegistration.getAmount(),
                reportResponse
        );
    }

    public static ReportResponse buildReportResponse(List<VehicleRegistration> vehiclesParked, List<VehicleRegistration> checkoutReport) {

        var parkedVehicles = new ArrayList<ParkedVehiclesResponse>();
        for (VehicleRegistration registration : vehiclesParked) {
            var parked = new ParkedVehiclesResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getCheckIn());
            parkedVehicles.add(parked);
        }

        var checkoutVehicles = new ArrayList<CheckoutVehiclesResponse>();
        for (VehicleRegistration registration : checkoutReport) {
            var checkout = new CheckoutVehiclesResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getDuration().toMinutes(),
                    registration.getAmount());
            checkoutVehicles.add(checkout);
        }


        return new ReportResponse(parkedVehicles, checkoutVehicles);
    }
}
