package com.wenceslau.v1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Estacionamento {

    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        System.out.println("Informe a capacidade do estacionamento");
        int capacidade = Integer.parseInt(ler.nextLine());

        //Declara as variaveis a serem usadas no sistema
        String logsSaidas = "";
        String[] placas = new String[capacidade];
        LocalDateTime[] horasEntrada = new LocalDateTime[capacidade];

        String option;
        do {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Adicionar número da placa");
            System.out.println("\t 0 - Sair do programa");
            option = ler.next();

            switch (option){
                case "1":
                    System.out.println("Informe o número de placa");
                    String placa = ler.next();
                    aplicacao(placa, placas, horasEntrada, logsSaidas);
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Informe uma opção valida");
            }

        } while (true);
    }

    public static void aplicacao(String placa, String[] placas, LocalDateTime[] horasEntrada, String logsSaidas) {

        //verifica se a placa ja existe, se, existir o processo é de saida
        int posicao = verificarPlaca(placa, placas);

        //igual a -1, placa nao existe
        if (posicao == -1) {
            entradaVeiculo(placa, placas, horasEntrada);

        } else {
            logsSaidas = saidaVeiculo(posicao,  placas, horasEntrada, logsSaidas);

        }
        imprimirRelatorio( placas, horasEntrada, logsSaidas);

    }

    private static int verificarPlaca(String placaEntrada, String[] placas) {
        //percorre o array de placa ate achar a placa de entrada
        for (int i = 0; i < placas.length; i++) {
            String placa = placas[i];
            if (placa != null) {
                if (placa.equals(placaEntrada)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static void entradaVeiculo(String placa, String[] placas, LocalDateTime[] horasEntrada) {

        int posicaoLivre = posicaoLivre(placas);

        //Vericia se o estacionamento ja esta lotado, se a posicao for -1 na ha vagas
        if (posicaoLivre == -1) {
            System.err.println("Não ha vagas");
        } else {
            //Hora da entrada na posiçao livre do array
            LocalDateTime entrada = LocalDateTime.now();

            //Guarda a placa e a hora entrada nos array
            placas[posicaoLivre] = placa;
            horasEntrada[posicaoLivre] = entrada;

            System.out.printf("Entrada do veículo de placa: %s realizada.%n", placa);
        }
    }

    private static int posicaoLivre(String[] array) {
        //percorre o array verifica qual a primeira posicao livre do array
        //se nao tiver posicao livre retorna -1, indica estacioamento lotado
        for (int i = 0; i < array.length; i++) {
            String placa = array[i];
            if (placa == null) {
                return i;
            }
        }
        return -1;
    }

    private static String saidaVeiculo(int posicao, String[] placas, LocalDateTime[] horasEntrada, String logsSaidas) {

        String placa = placas[posicao];
        LocalDateTime entrada = horasEntrada[posicao];
        LocalDateTime saida = LocalDateTime.now();

        //Calcula o tempo em minutos que o veiculo permaneceu, e o valor a pagar
        long minutos = Duration.between(entrada, saida).toMinutes();
        double valorPagar = calculaValorAPagar(minutos);

        System.out.printf("Saída do veículo placa %s. Tempo no estacionamento %s minutos. Valor a ser cobrado: %.2f %n",
                placa, minutos, valorPagar );

        //String para o log de saidas
        String historico = String.format("\tPlaca %s - tempo permanência: %d minutos - valor cobrado: %.2f %n", placa,
                minutos, valorPagar);

        logsSaidas += historico;
        placas[posicao] = null;
        horasEntrada[posicao] = null;

        return logsSaidas;
    }

    private static void imprimirRelatorio(String[] placas, LocalDateTime[] horasEntrada, String logsSaidas) {
        System.out.println("_______________________________________________________________________________");
        System.out.println("VEICULOS ESTACIONADOS: ");

        //Percorre o array para mostrar os veiculos no estacionamento
        for (int i = 0; i < placas.length; i++) {
            String placa = placas[i];
            if (placa == null){
                continue;
            }
            LocalDateTime dataHoraEntrada = horasEntrada[i];
            String dataFormatada = dataHoraEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            System.out.printf("\tPlaca %s \t Hora de entrada: %s %n", placa, dataFormatada);
        }

        //Mostra o log de saidas
        System.out.println();
        System.out.println("REGISTROS DE SAIDAS: ");
        System.out.printf("%s %n", logsSaidas);
    }

    private static double calculaValorAPagar(long tempoMinutos){
        /*
            • De 0 a 5 minutos. Sem cobrança de valor
            • De 5 a 60 minutos. R$ 4,00
            • Acima de 60 minutos é cobrado um valor de R$ 6,00 por hora adicional.
            • Sse ficou 1 hora e 1 minuto paga 4 pela primeira hora mais 6 pela hora adicional
        */
        if (tempoMinutos <= 5){
            return 0.0;
        }else if (tempoMinutos <= 60){
            return 4.0;
        }else {
            //Valor de uma hora
            double aPagar = 4.0;

            //Divide o tempo em minutos para saber o valor em horas
            // 125/60 = 2.08, entao foram 2.08 horas de permanencia

            // 1 hora = 4 reais
            // 1 hora = 6 reais
            // 0.8 hora = 6 reais

            //Horas em valor decimal
            double horas = (tempoMinutos /60.0);

            //Decrementa a primeira hora que tem o valor fixo de 4 reais
            horas--;

            //Arredonta o valor para cima
            horas = Math.ceil(horas);

            //Multiplica o valor pelo número de horas adicionais
            aPagar = aPagar + (horas * 6.0);
            return aPagar;
        }
    }
}
