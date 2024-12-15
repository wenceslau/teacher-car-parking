package com.wenceslau.v2;

import com.wenceslau.v3.Registro;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade do estacionamento: ");
        int capacidade = Integer.parseInt(scanner.nextLine());

        Estacionamento estacionamento = new Estacionamento(capacidade);
        String opcao;
        do {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Adicionar número da placa");
            System.out.println("\t 0 - Sair do programa");
            opcao = scanner.next();

            switch (opcao) {
                case "1" -> registrarPlaca(scanner, estacionamento);
                case "0" -> System.exit(0);
                default -> System.out.println("Informe uma opção valida");
            }

        } while (true);
    }

    private static void registrarPlaca(Scanner scanner, Estacionamento estacionamento) {
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();
        estacionamento.registrarPlaca(placa);
        estacionamento.exibirRelatorio();
    }
}


