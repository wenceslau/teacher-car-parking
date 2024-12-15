package com.wenceslau.v4.domain;

public abstract class Veiculo {

    private String placa;
    private double taxa;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public void validar(){
        if (placa == null || placa.length() != 8) {
            throw new IllegalArgumentException("Placa invalida. Deve ter 8 caracteres. AAA-0000");
        }
        if (taxa <= 0) {
            throw new IllegalArgumentException("Taxa deve ser maior que zero");
        }
    }

    protected abstract double valorPagar(long minutes);

}
