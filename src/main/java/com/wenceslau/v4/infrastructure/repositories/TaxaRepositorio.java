package com.wenceslau.v4.infrastructure.repositories;

import java.util.Map;

public class TaxaRepositorio {

    private final Map<String, Double> rates = Map.of(
            "CARRO", 4.0,
            "MOTO", 2.0,
            "PICKUP", 6.0,
            "BIKE", 1.0
    );

    public double getRateByType(String veiculoTipo) {

        if (veiculoTipo == null) {
            throw new IllegalArgumentException("Tipo de veículo não pode ser nulo");
        }

        if (!rates.containsKey(veiculoTipo.toUpperCase())) {
            throw new IllegalArgumentException("Taxa para tipo de veículo %s não encontrado".formatted(veiculoTipo.toUpperCase()));
        }

        // Realiza a busca da taxa de acordo com o tipo de veículo, em um repositorio de taxas

        return  rates.get(veiculoTipo.toUpperCase());
    }

}
