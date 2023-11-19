package edu.hw5;

public class Task7 {

    /**
     * Checks if a string contains at least 3 characters and the third character is 0.
     *
     * @param s The input string.
     * @return {@code true} if the string meets the criteria, {@code false} otherwise.
     */
    public static boolean hasThirdCharAsZero(String s) {
        return s.matches("^[01]{2}0[01]*$");
    }

    /**
     * Checks if a string starts and ends with the same character.
     *
     * @param s The input string.
     * @return {@code true} if the string starts and ends with the same character, {@code false} otherwise.
     */
    public static boolean startsAndEndsWithSameChar(String s) {
        return s.matches("^([01])(.*\\1)?$");
    }

    /**
     * Checks if a string's length is between 1 and 3 characters inclusive.
     *
     * @param s The input string.
     * @return {@code true} if the string's length is between 1 and 3 characters, {@code false} otherwise.
     */
    public static boolean isLengthOneToThree(String s) {
        return s.matches("^[01]{1,3}$");
    }

    private Task7() {}
}
