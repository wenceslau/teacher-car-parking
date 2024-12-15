package com.wenceslau.v4.infraestrutura.api.records;

import java.time.LocalDateTime;

public record RegistroResponse(
        String tipoRegistro,
        String placa,
        String veiculoTipo,
        Double taxa,
        LocalDateTime horaEntrada,
        LocalDateTime horaSaida,
        Long duracao,
        Double valorPagar,
        RelatorioResponse relatorioResponse) {
}
