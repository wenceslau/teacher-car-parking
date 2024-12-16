package com.wenceslau.v4.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoEstacionamento {

    private final int capacidade;
    private final List<VeiculoRegistro> registros;

    public VeiculoEstacionamento(int capacidade) {
        if (capacidade <= 0){
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        this.capacidade = capacidade;
        this.registros = new ArrayList<>();
    }

    public VeiculoRegistro registrarVeiculo(Veiculo veiculo) {

        if (veiculo == null){
            throw new IllegalArgumentException("Vehicle is required");
        }

        var optional = procurarRegistro(veiculo);

        return optional
                .map(x -> registrarSaida(x))
                .orElseGet(() -> registrarEntrada(veiculo));

//        if (optional.isEmpty()) {
//            return registrarEntrada(veiculo);
//        }else {
//            VeiculoRegistro registro = optional.get();
//            return registrarSaida(registro);
//        }
    }

    private Optional<VeiculoRegistro> procurarRegistro(Veiculo veiculo) {
        return registros.stream()
                .filter(r -> r.getVehicle().getPlaca().equals(veiculo.getPlaca()))
                .filter(r -> r.getHoraSaida() == null)
                .findFirst();
    }

    private VeiculoRegistro registrarEntrada(Veiculo veiculo) {
        int count = registros.stream()
                .filter(r -> r.getHoraSaida() == null)
                .mapToInt(r -> 1)
                .sum();

        if (count == capacidade) {
            throw new IllegalStateException("Parking lot is full");
        }

        var registration = new VeiculoRegistro(veiculo, LocalDateTime.now());
        registros.add(registration);
        return registration;
    }

    private VeiculoRegistro registrarSaida(VeiculoRegistro registration) {
        registration.setHoraSaida(LocalDateTime.now());
        return registration;
    }

    public List<VeiculoRegistro> getRegistros() {
        return registros;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
