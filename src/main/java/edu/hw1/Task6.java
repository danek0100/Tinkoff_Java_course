package edu.hw1;

import java.util.Arrays;

public class Task6 {

    /**
     * Initiates the process of finding the number of steps required to reach Kaprekar's constant (6174).
     *
     * @param num The four-digit starting number with at least one distinct digit.
     * @return The number of steps required to reach 6174, or -1 if the input is invalid.
     */
    public static int countK(int num) {
        // Check for invalid input
        if (num < 1001 || num > 9999 || allDigitsSame(num)) {
            return -1;
        }
        return countKRecursive(num);
    }

    /**
     * The recursive helper function to perform the actual logic.
     *
     * @param num The current number.
     * @return The number of steps required to reach 6174 from the current number.
     */
    private static int countKRecursive(int num) {
        // Base case: if the number is already 6174
        if (num == 6174) {
            return 0;
        }

        // Convert the number to a string and sort its digits
        String numStr = String.format("%04d", num);
        String ascending = sortString(numStr);
        String descending = new StringBuilder(ascending).reverse().toString();

        // Convert the sorted strings back to numbers and find the difference
        int ascNum = Integer.parseInt(ascending);
        int descNum = Integer.parseInt(descending);
        int diff = descNum - ascNum;

        // Recursive case: call the function with the new number and increment the step count
        return 1 + countKRecursive(diff);
    }

    /**
     * Checks if all digits of the given number are the same.
     *
     * @param num The number to check.
     * @return true if all digits are the same, false otherwise.
     */
    private static boolean allDigitsSame(int num) {
        char[] digits = String.format("%04d", num).toCharArray();
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] != digits[0]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sorts the digits of the given string in ascending order.
     *
     * @param input The string to sort.
     * @return The sorted string.
     */
    private static String sortString(String input) {
        char[] arr = input.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

    private Task6() {}
}
