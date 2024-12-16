package com.wenceslau.v5;

/*
    * This class is a factory only for demonstration purposes.
    * It is not part of the original project.
 */

import com.wenceslau.v4.domain.Veiculo;
import com.wenceslau.v4.domain.VeiculoTipo;
import com.wenceslau.v4.domain.veiculos.Carro;
import com.wenceslau.v4.domain.veiculos.Moto;
import com.wenceslau.v4.domain.veiculos.Pickup;

import java.util.Map;

public final class VehicleFactory {

    private VehicleFactory() {
    }

    private static final Map<String, Veiculo> vehicles = Map.of(
            "CAR", new Carro(),
            "MOTORCYCLE", new Moto(),
            "TRUCK", new Pickup()
    );

    public static Veiculo createVehicleMap(String vehicleType) {
        return vehicles.get(vehicleType);
    }

    public static Veiculo createVehicleSwitch(String vehicleType) {
        return switch (vehicleType) {
            case "CAR" -> new Carro();
            case "MOTORCYCLE" -> new Moto();
            case "TRUCK" -> new Pickup();
            default -> throw new IllegalArgumentException("Invalid vehicle type");
        };
    }

    public static Veiculo createVehicleEnum(VeiculoTipo veiculoTipo) {
        return veiculoTipo.getVeiculo();
    }


}
