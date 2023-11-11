package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to find all Fridays the 13th in a given year
 * and to find the next Friday the 13th from a given date.
 */
public class Task2 {

    private static final int THIRTEEN = 13;
    private static final int MONTH_IN_YEAR = 12;

    /**
     * Finds all Fridays that fall on the 13th of any month in a given year.
     *
     * @param year The year to find the Fridays 13th for.
     * @return A list of LocalDate objects representing each Friday the 13th in the year.
     */
    public static List<LocalDate> findFridays13InYear(int year) {
        if (year < LocalDate.MIN.getYear() || year > LocalDate.MAX.getYear()) {
            throw new IllegalArgumentException("Year must be valid and within the LocalDate limits.");
        }

        List<LocalDate> fridays13 = new ArrayList<>();
        LocalDate date = LocalDate.of(year, 1, THIRTEEN);

        for (int month = 1; month <= MONTH_IN_YEAR; month++) {
            date = date.withMonth(month);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays13.add(date);
            }
        }

        return fridays13;
    }

    /**
     * Finds the next Friday the 13th after a given date.
     *
     * @param from The date to start searching for the next Friday the 13th.
     * @return A LocalDate representing the next Friday the 13th after the given date.
     */
    public static LocalDate findNextFriday13(LocalDate from) {
        if (from == null) {
            throw new IllegalArgumentException("The date must not be null.");
        }

        TemporalAdjuster nextFriday13th = TemporalAdjusters.ofDateAdjuster((LocalDate date) -> {
            LocalDate next13th = date.withDayOfMonth(THIRTEEN);

            if (date.getDayOfMonth() >= THIRTEEN) {
                next13th = next13th.plusMonths(1);
            }

            while (next13th.getDayOfWeek() != DayOfWeek.FRIDAY) {
                next13th = next13th.plusMonths(1);
            }

            return next13th;
        });

        return from.with(nextFriday13th);
    }

    private Task2() {}
}
