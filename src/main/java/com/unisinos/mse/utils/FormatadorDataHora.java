package com.unisinos.mse.utils;

import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FormatadorDataHora {

    public static String formatarData(LocalDateTime data) {
        if (Objects.isNull(data)) return null;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataFormatada = data.format(formatador);
        return dataFormatada;
    }

    public static String formatarHora(LocalDateTime data) {
        if (Objects.isNull(data)) return null;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        var horaFormatada = data.format(formatador);
        return horaFormatada;
    }
}
