package com.wenceslau.v3;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a capacidade do Estacionamento 1: ");
        int capacidade = Integer.parseInt(scanner.nextLine());

        Estacionamento estacionamento = new Estacionamento(capacidade);
        String opcao;

        while (true) {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Adicionar número da placa");
            System.out.println("\t 0 - Sair do programa");
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> registrarPlaca(scanner, estacionamento);
                case "0" -> System.exit(0);
                default -> System.out.println("Informe uma opção valida");
            }
        }
    }

    private static void registrarPlaca(Scanner scanner, Estacionamento estacionamento) {
        System.out.print("Digite a placa do veículo: ");
        String placaEntrada = scanner.nextLine();
        var registro = estacionamento.registrarPlaca(placaEntrada);

        if (registro == null) {
            System.err.println("Não ha vagas");
            return;
        }

        if (registro.getSaida() == null) {
            System.out.printf("Entrada do veículo de placa: %s realizada.%n", placaEntrada);
        } else {
            System.out.printf("Saída do veículo de placa: %s. Tempo no estabelecimento: %d minutos. Valor a ser cobrado: R$ %.2f%n",
                    placaEntrada, registro.getDuracao().toMinutes(), registro.getValor());
        }
        imprimirRelatorio(estacionamento);
    }

    private static void imprimirRelatorio(Estacionamento estacionamento) {
        System.out.println("_______________________________________________________________________________");
        System.out.println("VEICULOS ESTACIONADOS: ");

        var registros = estacionamento.getRegistros();

        for (Registro registro : registros) {
            if (registro.getSaida() == null) {
                var dataFormatada = registro.getEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                System.out.printf("\tPlaca %s - Hora de entrada: %s %n", registro.getVeiculo().getPlaca(), dataFormatada);
            }
        }

        System.out.println();
        System.out.println("REGISTROS DE SAIDAS: ");

        for (Registro registro : registros) {
            if (registro.getSaida() != null) {
                var placa = registro.getVeiculo().getPlaca();
                var minutos = registro.getDuracao().toMinutes();
                var valorPagar = registro.getValor();
                System.out.printf("\tPlaca %s - tempo permanência: %d minutos - valor cobrado: %.2f %n", placa, minutos, valorPagar);
            }
        }
    }
}
