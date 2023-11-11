package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The {@code Task3} class provides a method to parse various date string formats into a {@link LocalDate} object.
 */
public class Task3 {

    // Chain of responsibility pattern to handle different date formats.
    private static final Function<String, Optional<LocalDate>> CHAIN = createChain();

    // Create chain of responsibility for date parsing.
    private static Function<String, Optional<LocalDate>> createChain() {
        Function<String, Optional<LocalDate>> standard = string -> {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-M-d")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
                return Optional.of(LocalDate.parse(string, formatter));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        };

        Function<String, Optional<LocalDate>> withSlashes = string -> {
            try {
                return Optional.of(LocalDate.parse(string, DateTimeFormatter.ofPattern("M/d/yyyy")));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        };

        Function<String, Optional<LocalDate>> withSlashesTwoDigitYear = string -> {
            try {
                return Optional.of(LocalDate.parse(string, DateTimeFormatter.ofPattern("M/d/yy")));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        };

        Function<String, Optional<LocalDate>> relative = string -> switch (string) {
            case "tomorrow" -> Optional.of(LocalDate.now().plusDays(1));
            case "today" -> Optional.of(LocalDate.now());
            case "yesterday" -> Optional.of(LocalDate.now().minusDays(1));
            default -> Optional.empty();
        };

        Function<String, Optional<LocalDate>> daysAgo = string -> {
            Matcher matcher = Pattern.compile("(\\d+) day(s)? ago").matcher(string);
            if (matcher.matches()) {
                int days = Integer.parseInt(matcher.group(1));
                return Optional.of(LocalDate.now().minusDays(days));
            }
            return Optional.empty();
        };

        return string -> {
            Optional<LocalDate> result = Optional.empty();
            if (string != null && !string.isEmpty()) {
                result = standard.apply(string);
                if (result.isEmpty()) {
                    result = withSlashes.apply(string);
                }
                if (result.isEmpty()) {
                    result = withSlashesTwoDigitYear.apply(string);
                }
                if (result.isEmpty()) {
                    result = relative.apply(string);
                }
                if (result.isEmpty()) {
                    result = daysAgo.apply(string);
                }
            }
            return result;
        };
    }

    /**
     * Parses a date string into a {@link LocalDate} object.
     *
     * The method supports various formats, including:
     * - ISO local date (e.g., "2020-10-10")
     * - ISO local date with no leading zeros (e.g., "2020-12-2")
     * - US format with slashes (e.g., "1/3/1976" or "1/3/20")
     * - Relative dates such as "tomorrow", "today", and "yesterday"
     * - Relative dates with a day count (e.g., "1 day ago" or "2234 days ago")
     *
     * If none of the formats are matched, {@link Optional#empty()} is returned.
     *
     * @param string the date string to parse
     * @return an {@link Optional} containing the parsed {@link LocalDate}, or {@link Optional#empty()} if not parsable
     */
    public static Optional<LocalDate> parseDate(String string) {
        return CHAIN.apply(string);
    }

    private Task3() {}
}
