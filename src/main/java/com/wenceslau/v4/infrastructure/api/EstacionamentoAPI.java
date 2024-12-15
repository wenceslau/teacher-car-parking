package com.wenceslau.v4.infrastructure.api;

import com.wenceslau.v4.aplicacao.EstacionamentoApp;
import com.wenceslau.v4.aplicacao.RelatorioApp;
import com.wenceslau.v4.domain.VeiculoEstacionamento;
import com.wenceslau.v4.infrastructure.api.records.Apresentacao;
import com.wenceslau.v4.infrastructure.repositories.TaxaRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("estacionamento")
public class EstacionamentoAPI {

    private final TaxaRepositorio taxaRepositorio = new TaxaRepositorio();
    private EstacionamentoApp estacionamentoApp;
    private RelatorioApp relatorioApp;

    @PostMapping("/criar/{capacity}")
    public ResponseEntity<?> criar(@PathVariable int capacity) {

        try {
            VeiculoEstacionamento veiculoEstacionamento = new VeiculoEstacionamento(capacity);
            estacionamentoApp = new EstacionamentoApp(veiculoEstacionamento, taxaRepositorio);
            relatorioApp = new RelatorioApp(veiculoEstacionamento);

            return ResponseEntity
                    .ok()
                    .body(Apresentacao.construirCriarResponseString(capacity));
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

