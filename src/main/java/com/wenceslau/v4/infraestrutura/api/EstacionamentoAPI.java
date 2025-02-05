package com.wenceslau.v4.infraestrutura.api;

import com.wenceslau.v4.aplicacao.EstacionamentoApp;
import com.wenceslau.v4.aplicacao.RelatorioApp;
import com.wenceslau.v4.domain.VeiculoEstacionamento;
import com.wenceslau.v4.infraestrutura.api.records.Apresentacao;
import com.wenceslau.v4.infraestrutura.repositories.TaxaRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("estacionamento")
public class EstacionamentoAPI {

    private final TaxaRepositorio taxaRepositorio = new TaxaRepositorio();
    private EstacionamentoApp estacionamentoApp;
    private RelatorioApp relatorioApp;

    @PostMapping("/criar/{capacity}")
    public ResponseEntity<?> criar(@PathVariable int capacidade) {

        try {
            VeiculoEstacionamento veiculoEstacionamento = new VeiculoEstacionamento(capacidade);
            estacionamentoApp = new EstacionamentoApp(veiculoEstacionamento, taxaRepositorio);
            relatorioApp = new RelatorioApp(veiculoEstacionamento);

            return ResponseEntity
                    .ok()
                    .body(Apresentacao.construirCriarResponseString(capacidade));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/registrar/{placa}/{tipo}")
    public ResponseEntity<?> registrar(@PathVariable String placa, @PathVariable String tipo) {

        try {
            var veiculoRegistro = estacionamentoApp.registrarPlacaVeiculo(placa, tipo);
            var veiculosEstacionados = relatorioApp.veiculosEstacionados();
            var registrosSaidas = relatorioApp.registrosSaidas();

            return ResponseEntity
                    .ok()
                    .body(Apresentacao.construirRegistrarResponseString(veiculoRegistro, veiculosEstacionados, registrosSaidas));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}

