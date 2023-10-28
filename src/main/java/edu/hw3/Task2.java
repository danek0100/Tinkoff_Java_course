package edu.hw3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Provides utility methods for clustering balanced parentheses in a string.
 */
public class Task2 {

    /**
     * Clusterizes a given string into groups of balanced parentheses.
     *
     * @param input The string to be clusterized.
     * @return A list of strings, where each string is a cluster of balanced parentheses.
     */
    public static List<String> clusterize(String input) {
        List<String> clusters = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        int clusterStart = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (isOpenBracket(c)) {
                handleOpenBracket(stack);
            } else if (isCloseBracket(c)) {
                clusterStart = handleCloseBracket(stack, clusters, clusterStart, i, input);
            }
        }

        handleRemainingOpenBrackets(stack, clusters, input, clusterStart);

        return clusters;
    }

    private static boolean isOpenBracket(char c) {
        return c == '(';
    }

    private static boolean isCloseBracket(char c) {
        return c == ')';
    }

    private static void handleOpenBracket(Deque<Character> stack) {
        stack.push('(');
    }

    private static int handleCloseBracket(Deque<Character> stack, List<String> clusters,
        int clusterStart, int currentIndex, String input) {
        if (!stack.isEmpty()) {
            stack.pop();
            if (stack.isEmpty()) {
                clusters.add(input.substring(clusterStart, currentIndex + 1));
                return currentIndex + 1;
            }
        } else {
            clusters.add(input.substring(clusterStart, currentIndex + 1));
            return currentIndex + 1;
        }
        return clusterStart;
    }

    private static void handleRemainingOpenBrackets(Deque<Character> stack, List<String> clusters,
        String input, int clusterStart) {
        if (!stack.isEmpty()) {
            clusters.add(input.substring(clusterStart));
        }
    }

    private Task2() {}
}
