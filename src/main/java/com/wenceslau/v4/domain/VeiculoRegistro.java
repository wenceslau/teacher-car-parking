package com.wenceslau.v4.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class VeiculoRegistro {

    private final Veiculo veiculo;
    private final LocalDateTime horaEntrada;

    private LocalDateTime horaSaida;
    private Duration duracao;
    private double valorPagar;

    public VeiculoRegistro(Veiculo veiculo, LocalDateTime horaEntrada) {
        this.veiculo = veiculo;
        this.horaEntrada = horaEntrada;
        validar();
    }

    public void setHoraSaida(LocalDateTime horaSaida) {

        if (horaSaida == null){
            throw new IllegalArgumentException("Check-out date is required");
        }
        if (horaSaida.isBefore(horaEntrada)){
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        this.horaSaida = horaSaida;
        this.duracao = Duration.between(horaEntrada, horaSaida);
        this.valorPagar = veiculo.valorPagar(duracao.toMinutes());
    }

    public Veiculo getVehicle() {
        return veiculo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    private void validar(){
        if (veiculo == null){
            throw new IllegalArgumentException("Vehicle is required");
        }
        if (horaEntrada == null){
            throw new IllegalArgumentException("Check-in date is required");
        }
        veiculo.validar();
    }

}
