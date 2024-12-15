package com.wenceslau.v4.infrastructure.api.records;

import java.time.LocalDateTime;

public record VeiculoEstacionadoResponse(
        String placa,
        LocalDateTime horaEntrada) {
}
