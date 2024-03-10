package com.ivannikov.webapp.util;

import com.ivannikov.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Period period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
