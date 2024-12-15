package com.wenceslau.v4.domain.veiculos;

import com.wenceslau.v4.domain.Veiculo;

public class Carro extends Veiculo {

    @Override
    protected double valorPagar(long minutos) {
        if (minutos <= 5) {
            return 0;
        } else if (minutos <= 60) {
            return getTaxa();
        } else {
            double valorHoraAdicional = 6.0;
            long horaExtra = (minutos - 60) / 60;
            double minutoExtra = (minutos - 60) % 60;

            return getTaxa() + (horaExtra * valorHoraAdicional) + (minutoExtra > 0 ? valorHoraAdicional : 0);
        }
    }
}
