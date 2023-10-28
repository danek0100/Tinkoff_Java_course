package edu.hw3;

/**
 * This class represents the Atbash cipher encoder.
 */
public class Task1 {

    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] REVERSED_ALPHABET = new StringBuilder(ALPHABET.length).append(ALPHABET)
            .reverse().toString().toCharArray();
    private static final char[] LOWER_ALPHABET = new String(ALPHABET).toLowerCase().toCharArray();
    private static final char[] LOWER_REVERSED_ALPHABET = new String(REVERSED_ALPHABET).toLowerCase().toCharArray();

    /**
     * Encodes the given string using the Atbash cipher.
     *
     * @param input The string to encode.
     * @return The encoded string.
     */
    public static String atbash(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            int idx;

            if (Character.isUpperCase(c)) {
                idx = indexOf(ALPHABET, c);
                result.append(idx >= 0 ? REVERSED_ALPHABET[idx] : c);
            } else if (Character.isLowerCase(c)) {
                idx = indexOf(LOWER_ALPHABET, c);
                result.append(idx >= 0 ? LOWER_REVERSED_ALPHABET[idx] : c);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    private Task1() {}
}
