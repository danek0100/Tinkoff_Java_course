package edu.hw3;

/**
 * Provides utility methods for converting Arabic numbers to Roman numerals.
 */
public class Task4 {

    // Constants representing Arabic and corresponding Roman numerals
    private static final int[] ARABIC_NUMBERS = {
        1000, 900, 500, 400,
        100, 90, 50, 40,
        10, 9, 5, 4, 1
    };

    private static final String[] ROMAN_NUMERALS = {
        "M", "CM", "D", "CD",
        "C", "XC", "L", "XL",
        "X", "IX", "V", "IV",
        "I"
    };

    /**
     * Converts the given Arabic number to its Roman numeral representation.
     *
     * @param num The Arabic number to be converted.
     * @return A string representing the Roman numeral equivalent of the given number.
     */
    public static String convertToRoman(int num) {
        StringBuilder result = new StringBuilder();
        int toConvertNum = num;

        for (int i = 0; i < ARABIC_NUMBERS.length; i++) {
            while (toConvertNum >= ARABIC_NUMBERS[i]) {
                toConvertNum -= ARABIC_NUMBERS[i];
                result.append(ROMAN_NUMERALS[i]);
            }
        }

        return result.toString();
    }

    // Private constructor to prevent instantiation.
    private Task4() {}
}
