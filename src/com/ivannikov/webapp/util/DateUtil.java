package com.ivannikov.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    private DateUtil() {}

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }
}
