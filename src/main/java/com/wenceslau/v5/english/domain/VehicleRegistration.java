package com.wenceslau.v5.english.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class VehicleRegistration {

    private final Vehicle vehicle;
    private final LocalDateTime checkIn;

    private LocalDateTime checkOut;
    private Duration duration;
    private double amount;

    public VehicleRegistration(Vehicle vehicle, LocalDateTime checkin) {
        this.vehicle = vehicle;
        this.checkIn = checkin;
        validate();
    }

    public void setCheckOut(LocalDateTime checkOut) {

        if (checkOut == null){
            throw new IllegalArgumentException("Check-out date is required");
        }
        if (checkOut.isBefore(checkIn)){
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        this.checkOut = checkOut;
        this.duration = Duration.between(checkIn, checkOut);
        this.amount = vehicle.amountToPay(duration.toMinutes());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public Duration getDuration() {
        return duration;
    }

    public double getAmount() {
        return amount;
    }

    private void validate(){
        if (vehicle == null){
            throw new IllegalArgumentException("Vehicle is required");
        }
        if (checkIn == null){
            throw new IllegalArgumentException("Check-in date is required");
        }
        vehicle.validate();
    }

}
