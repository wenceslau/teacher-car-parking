package com.wenceslau.v4.domain;

import com.wenceslau.v4.domain.veiculos.Carro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricoEstacionamentoTest {

    @Test
    void dadoVeiculoNaoEstacionado_quandoEstacionar_entaoVeiculoEstacionado() {

        // dado
        var veiculo = new Carro();
        veiculo.setPlaca("AAA-0000");
        veiculo.setTaxa(4.0);
        var capacidade = 10;
        var veiculoEstacionamento = new VeiculoEstacionamento(10);

        // quando
        veiculoEstacionamento.registrarVeiculo(veiculo);

        // então
        assertTrue(veiculoEstacionamento.getRegistros().stream().anyMatch(r -> r.getVehicle().getPlaca().equals(veiculo.getPlaca())));
    }

}
