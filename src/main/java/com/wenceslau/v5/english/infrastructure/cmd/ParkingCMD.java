package com.wenceslau.v5.english.infrastructure.cmd;

import com.wenceslau.v5.english.application.ParkingApp;
import com.wenceslau.v5.english.application.ReportApp;
import com.wenceslau.v5.english.domain.VehicleParking;
import com.wenceslau.v5.english.domain.VehicleRegistration;
import com.wenceslau.v5.english.infrastructure.repositories.RateRepository;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ParkingCMD {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the parking capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        var vehicleParking = new VehicleParking(capacity);
        var rateRepository = new RateRepository();

        var parking = new ParkingApp(vehicleParking, rateRepository);
        var report = new ReportApp(vehicleParking);

        String option;

        while (true) {
            System.out.println("===========================================================================");
            System.out.println("Options");
            System.out.println("\t 1 - Insert license plate and Vehicle Type");
            System.out.println("\t 0 - Exit");
            option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> register(scanner, parking, report);
                    case "0" -> System.exit(0);
                    default -> System.out.println("Inform a valid option");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void register(Scanner scanner, ParkingApp parkingApp, ReportApp reportApp) {

        System.out.println("Type the license plate and vehicle type. Ex: ABC-1234 CAR");
        var input = scanner.nextLine();
        var licensePlate = input.split(" ")[0];
        var vehicleType = input.split(" ")[1];

        var vehicleRegistration = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        if (vehicleRegistration.getCheckOut() == null) {
            System.out.printf("Entrance of the vehicle with plate: %s performed.%n", licensePlate);
        } else {
            System.out.printf("Exit of the vehicle with plate: %s. Time: %d minutes. Value to be charged: $ %.2f%n",
                    licensePlate, vehicleRegistration.getDuration().toMinutes(), vehicleRegistration.getAmount());
        }
        report(reportApp);
    }

    private static void report(ReportApp reportApp) {
        System.out.println("___________________________________________________________________________");
        System.out.println("VEHICLES PARKED: ");

        var vehiclesParked = reportApp.vehiclesParked();

        for (VehicleRegistration registration : vehiclesParked) {
            var checkIn = registration.getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            System.out.printf("\tLicense Plate %s - check-in date: %s %n", registration.getVehicle().getLicensePlate(), checkIn);

        }

        System.out.println();
        System.out.println("EXIT RECORDS: ");

        var checkoutLog = reportApp.checkoutLog();

        for (VehicleRegistration registration : checkoutLog) {
            var licensePlate = registration.getVehicle().getLicensePlate();
            var minutes = registration.getDuration().toMinutes();
            var amount = registration.getAmount();
            System.out.printf("\tLicense Plate %s - Time: %d minutes - Value charged: $ %.2f%n",
                    licensePlate, minutes, amount);
        }
    }
}
