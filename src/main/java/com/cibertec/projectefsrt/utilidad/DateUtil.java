package com.cibertec.projectefsrt.utilidad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(formatter) : "";
    }
}
