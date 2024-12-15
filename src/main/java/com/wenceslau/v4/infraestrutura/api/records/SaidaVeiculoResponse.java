package com.wenceslau.v4.infraestrutura.api.records;

public record SaidaVeiculoResponse(
        String placa,
        long duracao,
        double valorPagar) {
}
