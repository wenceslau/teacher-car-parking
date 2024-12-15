package com.wenceslau.v3;

public class Veiculo {

    private String placa;

    public Veiculo(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }


}
