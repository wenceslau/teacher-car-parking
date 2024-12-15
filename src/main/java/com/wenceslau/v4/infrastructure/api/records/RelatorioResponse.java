package com.wenceslau.v4.infrastructure.api.records;

import java.util.List;

public record RelatorioResponse(
        List<VeiculoEstacionadoResponse> parkedVehicles,
        List<SaidaVeiculoResponse> checkoutVehicles) {
}
