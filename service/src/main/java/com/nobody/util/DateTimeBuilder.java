package com.nobody.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeBuilder {
    private static final String DELIMITER = "-";
    private static final String YEAR = "yyyy";
    private static final String MONTH = "MM";
    private static final String DAY = "dd";
    private static final String PATTERN = YEAR + DELIMITER + MONTH + DELIMITER + DAY;

    public static String getCurrentDateYYYY_MM_ddFormat() {
        return getData(PATTERN);
    }

    public static String getYear() {
        return getData(YEAR);
    }

    public static String getMonth() {
        return getData(MONTH);
    }

    public static String getDay() {
        return getData(DAY);
    }

    private static String getData(String pattern) {
        ZonedDateTime mst = ZonedDateTime.now(ZoneId.of("America/Indiana/Indianapolis"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return mst.format(formatter);
    }

}
