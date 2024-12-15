package com.wenceslau.v4.infraestrutura.api.records;

import com.wenceslau.v4.domain.VeiculoRegistro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Apresentacao {

    /* Presentation using String Responses*/

    public static String construirCriarResponseString(int capacity) {
        return "Parking created with capacity: %d%n".formatted(capacity);
    }

    public static String construirRegistrarResponseString(VeiculoRegistro veiculoRegistro,
                                                          List<VeiculoRegistro> veiculosEstacionado,
                                                          List<VeiculoRegistro> regitrosSaida) {
        String mensagem;
        if (veiculoRegistro.getHoraSaida() == null) {
            mensagem = "Entrada do veículo com placa: %s%n"
                    .formatted(veiculoRegistro.getVehicle().getPlaca());
        } else {
            mensagem = "Saida do veículo com placa: %s. Tempo: %d minutos. Valor a ser cobrado: R$ %.2f%n"
                    .formatted(veiculoRegistro.getVehicle().getPlaca(), veiculoRegistro.getDuracao().toMinutes(), veiculoRegistro.getValorPagar());
        }

        mensagem += "\n";

        mensagem += construirRelatorioResponseString(veiculosEstacionado, regitrosSaida);

        return mensagem;
    }

    public static String construirRelatorioResponseString(List<VeiculoRegistro> vehiclesParked, List<VeiculoRegistro> checkoutReport) {
        var message = new StringBuilder();

        message.append("_______________________________________________________________________________\n");

        message.append("VEICULOS ESTACIONADO: \n");

        for (VeiculoRegistro registration : vehiclesParked) {
            var checkIn = registration.getHoraEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            message.append("\tPlaca %s - Hora entrada: %s %n".formatted(registration.getVehicle().getPlaca(), checkIn));
        }

        message.append("\n");

        message.append("REGISTROS DE SAIDA: \n");

        for (VeiculoRegistro registration : checkoutReport) {
            var licensePlate = registration.getVehicle().getPlaca();
            var minutes = registration.getDuracao().toMinutes();
            var amount = registration.getValorPagar();
            message.append("\tPlaca %s - Tempo: %d minutos - Valor Pago: R$ %.2f%n"
                    .formatted(licensePlate, minutes, amount));
        }

        message.append("_______________________________________________________________________________\n");

        return message.toString();

    }

    /* Presentation using Objects Responses*/

    public static CriarResponse construirCriarResponse(int capacity) {
        return new CriarResponse(capacity, LocalDateTime.now());
    }

    public static RegistroResponse construirRegistrarResponse(VeiculoRegistro veiculoRegistro,
                                                              List<VeiculoRegistro> vehiclesParked,
                                                              List<VeiculoRegistro> checkoutReport) {

        var reportResponse = construirRelatorioResponse(vehiclesParked, checkoutReport);

        var type = "Entrada";
        Long duration = null;
        if (veiculoRegistro.getHoraSaida() != null) {
            type = "Saida";
            duration = veiculoRegistro.getDuracao().toMinutes();
        }
        String className = veiculoRegistro.getVehicle().getClass().getSimpleName().toUpperCase();
        return new RegistroResponse(
                type,
                veiculoRegistro.getVehicle().getPlaca(),
                className,
                veiculoRegistro.getVehicle().getTaxa(),
                veiculoRegistro.getHoraEntrada(),
                veiculoRegistro.getHoraSaida(),
                duration,
                veiculoRegistro.getValorPagar(),
                reportResponse
        );
    }

    public static RelatorioResponse construirRelatorioResponse(List<VeiculoRegistro> vehiclesParked, List<VeiculoRegistro> checkoutReport) {

        var parkedVehicles = new ArrayList<VeiculoEstacionadoResponse>();
        for (VeiculoRegistro registration : vehiclesParked) {
            var parked = new VeiculoEstacionadoResponse(
                    registration.getVehicle().getPlaca(),
                    registration.getHoraEntrada());
            parkedVehicles.add(parked);
        }

        var checkoutVehicles = new ArrayList<SaidaVeiculoResponse>();
        for (VeiculoRegistro registration : checkoutReport) {
            var checkout = new SaidaVeiculoResponse(
                    registration.getVehicle().getPlaca(),
                    registration.getDuracao().toMinutes(),
                    registration.getValorPagar());
            checkoutVehicles.add(checkout);
        }


        return new RelatorioResponse(parkedVehicles, checkoutVehicles);
    }
}
