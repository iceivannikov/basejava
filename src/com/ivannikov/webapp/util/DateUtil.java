package com.ivannikov.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1,1);
    private DateUtil() {}

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }
}
