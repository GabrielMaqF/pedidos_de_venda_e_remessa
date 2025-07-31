package com.automatizacoes_java.pedidos_de_venda_e_remessa.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            // Logar o erro em um cenário real
            return null;
        }
    }

    public static LocalDateTime parseLocalDateTime(String dateStr, String timeStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDate datePart = LocalDate.parse(dateStr, DATE_FORMATTER);
            return datePart.atStartOfDay().plusSeconds(parseTimeInSeconds(timeStr));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static long parseTimeInSeconds(String timeStr) {
        String[] parts = timeStr.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return (hours * 3600) + (minutes * 60) + seconds;
    }
}
