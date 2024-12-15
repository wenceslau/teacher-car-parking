package com.wenceslau.v5.english.infrastructure.api.records;

public record CheckoutVehiclesResponse(
        String plate,
        long duration,
        double amount) {
}
