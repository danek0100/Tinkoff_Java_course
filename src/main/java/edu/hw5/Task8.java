package edu.hw5;

public class Task8 {

    /**
     * Checks if a string of {0, 1} has an odd length.
     */
    public static boolean isOddLength(String input) {
        return input.matches("^[01]([01]{2})*$");
    }

    /**
     * Checks if a string starts with 0 and has an odd length, or starts with 1 and has an even length.
     */
    public static boolean startsWith0OddOr1Even(String input) {
        return input.matches("^(0([01]{2})*|1[01](([01]{2})*))$");
    }

    /**
     * Checks if a string of {0, 1} has a number of 0's that is a multiple of 3.
     */
    public static boolean zeroMultipleOfThree(String input) {
        return input.matches("^(1*01*01*0)*1*$");
    }

    /**
     * Checks if a string of {0, 1} is anything other than "11" or "111".
     */
    public static boolean not11Or111(String input) {
        return input.matches("^(?!11$)(?!111$)[01]+$");
    }

    /**
     * Checks if every odd position character in a string of {0, 1} is '1'.
     */
    public static boolean oddPositionIsOne(String input) {
        return input.matches("^(1[10]{0,1})*$");
    }

    /**
     * Checks if a string contains at least two '0' and at most one '1'.
     */
    public static boolean atLeastTwoZerosAtMostOneOne(String input) {
        return input.matches("^(0{2,}10*|10{2,}|0{1,}10{1,})$");
    }

    /**
     * Checks if a string of {0, 1} has no consecutive '1's.
     */
    public static boolean noConsecutiveOnes(String input) {
        return input.matches("^(?!.*11)[01]*$");
    }

    private Task8() {}
}
