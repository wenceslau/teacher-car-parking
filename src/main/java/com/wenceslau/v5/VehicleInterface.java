package com.wenceslau.v5;

/*
    * This interface is only for demonstration purposes.
    * It is not part of the original code.
 */

public interface VehicleInterface {

    void rate(double rate);

    void licensePlate(String licensePlate);

    String licensePlate();

    void validate();

    double amountToPay(long minutes);

    default double amountToPay(long minutes, double rate) {
        if (minutes <= 5) {
            return 0;
        } else if (minutes <= 60) {
            return rate;
        } else {
            long extraHours = (minutes - 60) / 60;
            double extraMinutes = (minutes - 60) % 60;
            return rate + (extraHours * 6) + (extraMinutes > 0 ? 6 : 0);
        }
    }
}
