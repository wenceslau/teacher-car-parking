package com.wenceslau.v4.infrastructure.cmd;

import com.wenceslau.v4.aplicacao.EstacionamentoApp;
import com.wenceslau.v4.aplicacao.RelatorioApp;
import com.wenceslau.v4.domain.VeiculoEstacionamento;
import com.wenceslau.v4.domain.VeiculoRegistro;
import com.wenceslau.v4.infrastructure.repositories.TaxaRepositorio;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EstacionamentoCMD {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a capacidade do Estacionamento 1: ");
        int capacidade = Integer.parseInt(scanner.nextLine());

        var veiculoEstacionamento = new VeiculoEstacionamento(capacidade);
        var taxaRepositorio = new TaxaRepositorio();

        var estacionamentoApp = new EstacionamentoApp(veiculoEstacionamento, taxaRepositorio);
        var relatorioApp = new RelatorioApp(veiculoEstacionamento);

        String opcao;

        while (true) {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Digite a placa e o tipo do veículo");
            System.out.println("\t 0 - Sair");
            opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1" -> registrar(scanner, estacionamentoApp, relatorioApp);
                    case "0" -> System.exit(0);
                    default -> System.out.println("Opção inválida");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void registrar(Scanner scanner, EstacionamentoApp estacionamentoApp, RelatorioApp relatorioApp) {

        System.out.println("Digite a placa e o tipo do veículo. Ex: ABC-1234 CARRO");
        var input = scanner.nextLine();
        var licensePlate = input.split(" ")[0];
        var vehicleType = input.split(" ")[1];

        var vehicleRegistration = estacionamentoApp.registrarPlacaVeiculo(licensePlate, vehicleType);

        if (vehicleRegistration.getHoraSaida() == null) {
            System.out.printf("Entrada do veículo com placa: %s realizada.%n", licensePlate);
        } else {
            System.out.printf("Saida do veículo com placa: %s. Tempo: %d minutos. Valor a ser cobrado: R$ %.2f%n",
                    licensePlate, vehicleRegistration.getDuracao().toMinutes(), vehicleRegistration.getValorPagar());
        }
        relatorio(relatorioApp);
    }

    private static void relatorio(RelatorioApp relatorioApp) {
        System.out.println("___________________________________________________________________________");
        System.out.println("VEICULOS ESTACIONADOS: ");

        var vehiclesParked = relatorioApp.veiculosEstacionados();

        for (VeiculoRegistro registration : vehiclesParked) {
            var checkIn = registration.getHoraEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            System.out.printf("\tPlaca %s - Hora entrada: %s %n", registration.getVehicle().getPlaca(), checkIn);

        }

        System.out.println();
        System.out.println("REGISTROS DE SAIDA: ");

        var checkoutLog = relatorioApp.registrosSaidas();

        for (VeiculoRegistro registration : checkoutLog) {
            var licensePlate = registration.getVehicle().getPlaca();
            var minutes = registration.getDuracao().toMinutes();
            var amount = registration.getValorPagar();
            System.out.printf("\tPlaca %s - Tempo: %d minutes - Valor a ser cobrado: R$ %.2f%n",
                    licensePlate, minutes, amount);
        }
    }
}
