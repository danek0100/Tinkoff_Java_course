package edu.hw1;


public class Task2 {

    private final static int BASE = 10;

    /**
     * Returns the number of digits in the decimal form of a given number.
     * <p>
     * The function takes into account both positive and negative numbers.
     * Negative numbers are treated as their absolute values. If a positive
     * representation cannot be obtained (as in the case of Long.MIN_VALUE),
     * the function returns -1.
     * </p>
     *
     * @param number The input number whose digits are to be counted.
     * @return The number of digits in the decimal form of the input number,
     *         or -1 if obtaining a positive representation is not possible.
     */
    public static long countDigits(long number) {
        // Take the absolute value in case the number is negative
        long absoluteNumber = Math.abs(number);

        if (absoluteNumber < 0) {
            return -1;
        }

        long power = 0;
        long digitsNum = 1;
        do {
            digitsNum *= BASE;
            power++;
        } while (absoluteNumber / digitsNum > 0);

        return power;
    }

    private Task2() {}
}
