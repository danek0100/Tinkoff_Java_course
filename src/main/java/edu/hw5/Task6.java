package edu.hw5;

public class Task6 {

    /**
     * Determines if the string S is a consecutive subsequence of the string T using regex.
     *
     * @param s The subsequence string.
     * @param t The target string.
     * @return {@code true} if S is a consecutive subsequence of T, {@code false} otherwise.
     */
    public static boolean isConsecutiveSubsequenceRegex(String s, String t) {
        StringBuilder regex = new StringBuilder();
        for (char c : s.toCharArray()) {
            regex.append(c);
        }
        return t.matches(".*" + regex.toString() + ".*");
    }

    private Task6() {}
}
