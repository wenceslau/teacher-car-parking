package com.wenceslau.v4.infraestrutura.api.records;

import java.util.List;

public record RelatorioResponse(
        List<VeiculoEstacionadoResponse> parkedVehicles,
        List<SaidaVeiculoResponse> checkoutVehicles) {
}
