package com.wenceslau.v4.domain;

import com.wenceslau.v4.domain.veiculos.Carro;
import com.wenceslau.v4.domain.veiculos.Moto;
import com.wenceslau.v4.domain.veiculos.Pickup;

import java.util.Arrays;

public enum VeiculoTipo {
    CARRO,
    MOTO,
    PICKUP;

    public Veiculo getVeiculo() {
        return switch (this) {
            case CARRO -> new Carro();
            case MOTO -> new Moto();
            case PICKUP -> new Pickup();
        };
    }

    public static VeiculoTipo converter(String vehicleType) {
        try {
            return VeiculoTipo.valueOf(vehicleType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo veiculo invalido. Deve ser: " + Arrays.toString(VeiculoTipo.values()));
        }
    }

}
