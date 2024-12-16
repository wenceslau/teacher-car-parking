package com.wenceslau.v4.domain.veiculos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

    @Test
    void dadoCarroComPlacaInvalida_quandoValidar_entaoDeveLancarExcecao() {
        // dado
        Carro carro = new Carro();
        carro.setPlaca("ABC1234");
        carro.setTaxa(1.0);

        // quando / então
        assertThrows(IllegalArgumentException.class, carro::validar);
    }

    @Test
    void dadoCarroComTaxaInvalida_quandoValidar_entaoDeveLancarExcecao() {
        // dado
        Carro carro = new Carro();
        carro.setPlaca("ABC-1234");
        carro.setTaxa(-1.0);

        // quando / então
        assertThrows(IllegalArgumentException.class, carro::validar);
    }

    @Test
    void dadoCarroComValoresValidos_entaoNaoDeveLancarExcecao() {
        // dado
        Carro carro = new Carro();
        carro.setPlaca("ABC1234");
        carro.setTaxa(1.0);

        // quando / então
        assertDoesNotThrow(carro::validar);
    }

}
