package com.wenceslau.v5.english.infrastructure.api.records;

import java.util.List;

public record ReportResponse(
        List<ParkedVehiclesResponse> parkedVehicles,
        List<CheckoutVehiclesResponse> checkoutVehicles) {
}
