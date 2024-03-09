package com.ivannikov.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.now();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    private DateUtil() {
    }

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String date) {
        if (date == null || date.trim().isEmpty() || "Сейчас".equals(date)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
