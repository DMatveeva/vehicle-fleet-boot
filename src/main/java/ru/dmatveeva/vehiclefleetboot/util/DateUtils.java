package ru.dmatveeva.vehiclefleetboot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime getLdtFromString(String ldtStr) {
        DateTimeFormatter formatter;
        if (ldtStr.length() == 19) {
            formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        } else if (ldtStr.length() == 18) {
            formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        } else {
            throw new RuntimeException();
        }
        return LocalDateTime.parse(ldtStr, formatter);
    }

    public static LocalDateTime getLtdInTimeZone(LocalDateTime dateTime, String zoneIdFrom, String zoneIdTo) {
        return dateTime.atZone(ZoneId.of(zoneIdFrom))
                .withZoneSameInstant(ZoneId.of(zoneIdTo))
                .toLocalDateTime();
    }

    public static ZonedDateTime getZdtFromLdtInUtc(LocalDateTime ldtUtc, String tz) {
        ZonedDateTime zdtUtc = ldtUtc.atZone(ZoneId.of("UTC"));
        return zdtUtc.withZoneSameInstant(ZoneId.of(tz));
    }

    // MM-dd-yyyy
    public static LocalDate getLocalDateFromString(String s) {
        String[] arrStart = s.split("/");
        int month = Integer.parseInt(arrStart[0]);
        int day = Integer.parseInt(arrStart[1]);
        int year = Integer.parseInt(arrStart[2]);
        return LocalDate.of(year, month, day);
    }
}
