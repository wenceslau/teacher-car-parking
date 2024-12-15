package com.wenceslau.v4.infrastructure.api.records;

public record SaidaVeiculoResponse(
        String placa,
        long duracao,
        double valorPagar) {
}
