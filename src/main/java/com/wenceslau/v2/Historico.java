package com.wenceslau.v2;

public class Historico {

    private String placa;
    private long tempo;
    private double valor;

    public Historico(String placa, long tempo, double valor) {
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
