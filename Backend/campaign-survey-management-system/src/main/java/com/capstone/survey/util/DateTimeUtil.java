package com.capstone.survey.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}