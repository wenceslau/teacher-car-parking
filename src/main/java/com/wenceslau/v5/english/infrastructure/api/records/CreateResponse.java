package com.wenceslau.v5.english.infrastructure.api.records;


import java.time.LocalDateTime;

public record CreateResponse(
        int capacity,
        LocalDateTime createdAt) {
}
