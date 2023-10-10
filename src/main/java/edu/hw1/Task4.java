package edu.hw1;

public class Task4 {

    /**
     * Fixes a string by swapping each pair of characters.
     *
     * @param input The input string that needs to be fixed.
     * @return A fixed string where each pair of characters is swapped.
     */
    public static String fixString(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < input.length(); i += 2) {
            sb.append(input, i, i + 1);
            sb.append(input, i - 1, i);
        }

        if (input.length() % 2 != 0) {
            sb.append(input, input.length() - 1, input.length());
        }

        return sb.toString();
    }

    private Task4() {}
}
