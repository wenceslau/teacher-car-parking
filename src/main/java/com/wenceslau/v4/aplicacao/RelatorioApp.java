package com.wenceslau.v4.aplicacao;

import com.wenceslau.v4.domain.VeiculoEstacionamento;
import com.wenceslau.v4.domain.VeiculoRegistro;

import java.util.List;

public class RelatorioApp {

    private final VeiculoEstacionamento veiculoEstacionamento;

    public RelatorioApp(VeiculoEstacionamento veiculoEstacionamento) {
        this.veiculoEstacionamento = veiculoEstacionamento;
    }

    public List<VeiculoRegistro> veiculosEstacionados() {
        return veiculoEstacionamento.getRegistros().stream()
                .filter(r -> r.getHoraSaida() == null)
                .toList();
    }

    public List<VeiculoRegistro> registrosSaidas() {
        return veiculoEstacionamento.getRegistros().stream()
                .filter(r -> r.getHoraSaida() != null)
                .toList();
    }

}
