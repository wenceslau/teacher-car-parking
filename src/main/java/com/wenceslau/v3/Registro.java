package com.wenceslau.v3;

import java.time.Duration;
import java.time.LocalDateTime;

public class Registro {

    private final Veiculo veiculo;
    private final LocalDateTime entrada;

    private LocalDateTime saida;
    private Duration duracao;
    private double valor;

    public Registro(Veiculo veiculo, LocalDateTime entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("Entrada não pode ser nula");
        }
        this.veiculo = veiculo;
        this.entrada = entrada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        if (saida == null) {
            throw new IllegalArgumentException("Saída não pode ser nula");
        }
        this.saida = saida;
        this.duracao = Duration.between(entrada, saida);
        this.valor = calcularValorPagar(duracao.toMinutes());
    }

    public Duration getDuracao() {
        return duracao;
    }

    public double getValor() {
        return valor;
    }

    /* Metodos Privados */

    private double calcularValorPagar(long tempoMinutos){

        //        • De 0 a 5 minutos. Sem cobrança de valor
        //        • De 5 a 60 minutos. R$ 4,00
        //        • Acima de 60 minutos é cobrado um valor de R$ 6,00 por hora adicional.
        //        • Sse ficou 1 hora e 1 minuto paga 4 pela primeira hora mais 6 pela hora adicional

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
