package com.wenceslau.v3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

    private final int capacidade;
    private final List<Registro> registros = new ArrayList<>();

    public Estacionamento(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public Registro registrarPlaca(String placa) {
        Registro registro = buscarRegitro(placa);
        if (registro == null) {
            registro = registrarEntrada(placa);
        } else {
            registrarSaida(registro);
        }
        return registro;
    }


    /* Metodos Privados */

    private Registro registrarEntrada(String placa) {
        int veiculosEstacionados = 0;

        for (Registro registro : registros) {
            if (registro.getSaida() == null) {
                veiculosEstacionados++;
            }
        }

        if (veiculosEstacionados == capacidade) {
            return null;
        }

        Veiculo veiculo = new Veiculo(placa);
        Registro registro = new Registro(veiculo, LocalDateTime.now());
        registros.add(registro);

        return registro;
    }

    private void registrarSaida(Registro registro) {
        registro.setSaida(LocalDateTime.now());
    }

    private Registro buscarRegitro(String placa) {
        for (Registro registro : registros) {
            if (registro.getVeiculo().getPlaca().equals(placa) && registro.getSaida() == null) {
                return registro;
            }
        }
        return null;
    }


}
