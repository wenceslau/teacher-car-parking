package com.wenceslau.v2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Estacionamento {

    private int capacidade;
    private HashMap<String, LocalDateTime> veiculosAtuais;
    private ArrayList<Veiculo> historico;

    public Estacionamento(int capacidade) {
        this.capacidade = capacidade;
        this.veiculosAtuais = new HashMap<>();
        this.historico = new ArrayList<>();
    }

    public void registrarPlaca(String placa) {
        if (veiculosAtuais.size() >= capacidade) {
            System.err.println("Não ha vagas");
            return;
        }

        if (veiculosAtuais.containsKey(placa)) {
            registrarSaida(placa);
        } else {
            registrarEntrada(placa);
        }

        veiculosAtuais.put(placa, LocalDateTime.now());
    }

    private void registrarEntrada(String placa) {
        veiculosAtuais.put(placa, LocalDateTime.now());
        System.out.printf("Entrada do veículo de placa: %s realizada. %n", placa);
    }

    private void registrarSaida(String placa) {
        LocalDateTime horaEntrada = veiculosAtuais.get(placa);
        LocalDateTime horaSaida = LocalDateTime.now();
        veiculosAtuais.remove(placa);

        long minutosPermanencia = Duration.between(horaEntrada, horaSaida).toMinutes();
        double valorCobrado = calcularValor(minutosPermanencia);

        System.out.printf("Saída do veículo de placa: %s. Tempo no estabelecimento: %d minutos. Valor a ser cobrado: R$ %.2f %n",
                placa, minutosPermanencia, valorCobrado);

        historico.add(new Veiculo(placa, minutosPermanencia, valorCobrado));
    }

    private double calcularValor(long minutos) {
        if (minutos <= 5) {
            return 0;
        } else if (minutos <= 60) {
            return 4;
        } else {
            long horasAdicionais = (minutos - 60) / 60;
            double minutosExtras = (minutos - 60) % 60;
            return 4 + (horasAdicionais * 6) + (minutosExtras > 0 ? 6 : 0);
        }
    }

    public void exibirRelatorio() {

        System.out.println("_______________________________________________________________________________");
        System.out.println("VEICULOS ESTACIONADOS: ");

        for (String placa : veiculosAtuais.keySet()) {
            String dataFormatada = veiculosAtuais.get(placa).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            System.out.printf("\tPlaca %s \t Hora de entrada: %s %n", placa, dataFormatada);
        }

        //Mostra o log de saidas
        System.out.println();
        System.out.println("REGISTROS DE SAIDAS: ");

        for (Veiculo veiculo : historico) {
            String historico = String.format("\tPlaca %s - tempo permanência: %d minutos - valor cobrado: %.2f %n",
                    veiculo.getPlaca(), veiculo.getTempo(), veiculo.getValor());
            System.out.println(historico);
        }
    }
}
