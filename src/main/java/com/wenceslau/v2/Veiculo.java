package com.wenceslau.v2;

public class Veiculo {

    private String placa;
    private long tempo;
    private double valor;

    public Veiculo(String placa, long tempo, double valor) {
        this.placa = placa;
        this.tempo = tempo;
        this.valor = valor;
    }

    public String getPlaca() {
        return placa;
    }

    public long getTempo() {
        return tempo;
    }

    public double getValor() {
        return valor;
    }
}
