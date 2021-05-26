package com.unisinos.mse.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatadorDataHora {

    public static String formatarData(LocalDateTime data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataFormatada = data.format(formatador);
        return dataFormatada;
    }

    public static String formatarHora(LocalDateTime data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        var horaFormatada = data.format(formatador);
        return horaFormatada;
    }
}
