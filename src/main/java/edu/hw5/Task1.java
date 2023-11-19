package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class provides functionality to analyze session times in a computer club.
 */
public class Task1 {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd, HH:mm";
    private static final String SPLIT_ELEMENT = " - ";
    private static final  Logger LOGGER = LogManager.getLogger();
    private static final int MINUTES_IN_HOUR = 60;

    /**
     * Calculates the average session time from a list of session strings.
     *
     * @param sessions Array of session strings in the format "yyyy-MM-dd, HH:mm - yyyy-MM-dd, HH:mm"
     * @return Average session time in the format "Xh Ym"
     */
    public static String getAverageSessionTime(String[] sessions) {
        if (sessions == null || sessions.length == 0) {
            throw new IllegalArgumentException("Session array must not be null or empty.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN, Locale.ENGLISH);
        Duration totalDuration = Duration.ZERO;
        int validSessionsCount = 0;

        for (String session : sessions) {
            try {
                String[] times = session.split(SPLIT_ELEMENT);
                LocalDateTime startTime = LocalDateTime.parse(times[0], formatter);
                LocalDateTime endTime = LocalDateTime.parse(times[1], formatter);

                if (!endTime.isAfter(startTime)) {
                    throw new IllegalArgumentException("End time must be after start time.");
                }

                Duration duration = Duration.between(startTime, endTime);
                totalDuration = totalDuration.plus(duration);
                validSessionsCount++;
            } catch (DateTimeParseException e) {
                LOGGER.error("Skipping invalid session format: " + session);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Skipping invalid session time: " + session);
            }
        }

        if (validSessionsCount == 0) {
            throw new IllegalArgumentException("No valid sessions were provided.");
        }

        long averageMinutes = totalDuration.toMinutes() / validSessionsCount;
        long hours = averageMinutes / MINUTES_IN_HOUR;
        long minutes = averageMinutes % MINUTES_IN_HOUR;

        return String.format("%dh %dm", hours, minutes);
    }

    private Task1() {}
}
