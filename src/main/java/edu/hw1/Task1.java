package edu.hw1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods for converting video duration formats.
 */
public class Task1 {

    /**
     * Converts a video duration string from mm:ss format to total seconds.
     * <p>
     * The input string must be in mm:ss format. The minutes can be any positive
     * integer, but the seconds must be between 00 and 59 inclusive.
     * </p>
     *
     * @param input The video duration string in mm:ss format.
     * @return The total video duration in seconds. Returns -1 if the input string
     *         is invalid or if the seconds are 60 or more.
     */
    public static long minutesToSeconds(String input) {
        // Check the format of the input string using a regular expression
        Pattern pattern = Pattern.compile("^\\d+:\\d{1,2}$");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            return -1;
        }

        try {
            long seconds = Long.parseLong(input.substring(input.lastIndexOf(':') + 1));
            if (seconds >= 60) {
                return -1;
            }

            long minutes = Long.parseLong(input.substring(0, input.lastIndexOf(':')));
            return minutes * 60 + seconds;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Task1() {}
}
