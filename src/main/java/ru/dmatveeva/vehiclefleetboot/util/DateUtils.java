package ru.dmatveeva.vehiclefleetboot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime getLdtFromString(String ldtStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
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
    public static LocalDate getLocalDateFromString(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(s, formatter);
    }

    public static LocalDate getStartForReport(String period, LocalDate start) {
        return switch (period) {
            case "year" -> LocalDate.of(start.getYear(), 1, 1);
            case "month" -> LocalDate.of(start.getYear(), start.getMonth(), 1);
            case "day" -> start;
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        };
    }

    public static LocalDate getEndForReport(String period, LocalDate end) {
        LocalDate newEnd;
        switch (period) {
            case "year" -> newEnd = LocalDate.of(end.getYear(), 12, 31);
            case "month" -> {
                int lastDayOfMonth = end.getMonth().length(end.isLeapYear());
                newEnd = LocalDate.of(end.getYear(), end.getMonth(), lastDayOfMonth);
            }
            case "day" -> newEnd = end;
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        }
        return newEnd;
    }
}
