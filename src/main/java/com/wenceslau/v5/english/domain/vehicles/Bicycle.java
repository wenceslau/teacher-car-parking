package com.wenceslau.v5.english.domain.vehicles;

import com.wenceslau.v5.english.domain.Vehicle;

public class Bicycle extends Vehicle {

    @Override
    protected double amountToPay(long minutes) {
        if (minutes <= 5) {
            return 0;
        } else if (minutes <= 60) {
            return getRate();
        } else {
            double additionalHourValue = 6.0;
            long extraHours = (minutes - 60) / 60;
            double extraMinutes = (minutes - 60) % 60;

            return getRate() + (extraHours * additionalHourValue) + (extraMinutes > 0 ? additionalHourValue : 0);
        }
    }
}
