package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        checkClusterization("()()()", List.of("()", "()", "()"));
        checkClusterization("((()))", List.of("((()))"));
        checkClusterization("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())"));
        checkClusterization("((())())(()(()()))", List.of("((())())", "(()(()()))"));
    }

    @Test
    @DisplayName("Additional Test Cases")
    void additionalTestCases() {
        checkClusterization("", List.of()); // Empty string
        checkClusterization("()", List.of("()")); // Single pair
        checkClusterization("(", List.of("(")); // Single opening bracket
        checkClusterization(")", List.of(")")); // Single closing bracket
        checkClusterization("(())(())", List.of("(())", "(())")); // Nested clusters
        checkClusterization("(()())", List.of("(()())"));
        checkClusterization("((()))()", List.of("((()))", "()"));
    }

    private void checkClusterization(String input, List<String> expected) {
        assertEquals(expected, Task2.clusterize(input));
    }
}
