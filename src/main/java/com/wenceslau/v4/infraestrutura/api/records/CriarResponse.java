package com.wenceslau.v4.infraestrutura.api.records;


import java.time.LocalDateTime;

public record CriarResponse(
        int capacidade,
        LocalDateTime criadoEm) {
}
