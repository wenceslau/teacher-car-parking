package com.wenceslau.v5.english.domain;

public abstract class Vehicle {

    private String licensePlate;
    private double rate;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void validate(){
        if (licensePlate == null || licensePlate.length() != 8) {
            throw new IllegalArgumentException("License plate is invalid. Format must be: XXX-0000");
        }
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }
    }

    protected abstract double amountToPay(long minutes);

}
