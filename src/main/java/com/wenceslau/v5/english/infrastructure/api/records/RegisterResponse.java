package com.wenceslau.v5.english.infrastructure.api.records;

import java.time.LocalDateTime;

public record RegisterResponse(
        String type,
        String plate,
        String vehicleType,
        Double rate,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Long duration,
        Double amountToPay,
        ReportResponse reportResponse) {
}
