package com.wenceslau.v4.aplicacao;

import com.wenceslau.v4.domain.Veiculo;
import com.wenceslau.v4.domain.VeiculoEstacionamento;
import com.wenceslau.v4.domain.VeiculoRegistro;
import com.wenceslau.v4.domain.VeiculoTipo;
import com.wenceslau.v4.infraestrutura.repositories.TaxaRepositorio;

public class EstacionamentoApp {

    private final VeiculoEstacionamento veiculoEstacionamento;
    private final TaxaRepositorio taxaRepositorio;

    public EstacionamentoApp(VeiculoEstacionamento veiculoEstacionamento, TaxaRepositorio taxaRepositorio) {
        this.veiculoEstacionamento = veiculoEstacionamento;
        this.taxaRepositorio = taxaRepositorio;
    }

    public VeiculoRegistro registrarPlacaVeiculo(String placa, String veiculoTipo) {

        // Convert the string to the VehicleType enum
        VeiculoTipo tipo = VeiculoTipo.converter(veiculoTipo);

        Veiculo veiculo = tipo.getVeiculo();
        veiculo.setPlaca(placa);

        double taxa = taxaRepositorio.getRateByType(veiculoTipo);
        veiculo.setTaxa(taxa);

        return this.veiculoEstacionamento.registrarVeiculo(veiculo);
    }

}
