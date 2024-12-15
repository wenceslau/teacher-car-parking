package com.wenceslau.v4.domain;

import com.wenceslau.v4.domain.veiculos.Carro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoRegistroTest {

    @Test
    void dadoVeiculoRegistroValido_quandoValidar_entaoNaoDeveLancarExcecao() {
        // dado
        var veiculo = new Carro();
        veiculo.setPlaca("ABC-1234");
        veiculo.setTaxa(1.0);

        // qunando / então
        assertDoesNotThrow(() -> {
            new VeiculoRegistro(veiculo, LocalDateTime.now());
        });
    }
}
