package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        assertEquals(Map.of("a", 2, "bb", 2), Task3.freqDict(List.of("a", "bb", "a", "bb")));
        assertEquals(Map.of("this", 1, "and", 2, "that", 1), Task3.freqDict(List.of("this", "and", "that", "and")));
        assertEquals(Map.of("код", 3, "bug", 1), Task3.freqDict(List.of("код", "код", "код", "bug")));
        assertEquals(Map.of(1, 2, 2, 2), Task3.freqDict(List.of(1, 1, 2, 2)));
    }

    @Test
    @DisplayName("Empty List Case")
    void testEmptyList() {
        assertEquals(Map.of(), Task3.freqDict(List.of())); // Empty list
    }

    @Test
    @DisplayName("Single Item List Case")
    void testSingleItemList() {
        assertEquals(Map.of("single", 1), Task3.freqDict(List.of("single"))); // Single item list
    }

    @Test
    @DisplayName("Distinct Items List Case")
    void testDistinctItemsList() {
        assertEquals(Map.of(1, 1, 2, 1, 3, 1), Task3.freqDict(List.of(1, 2, 3))); // Distinct items list
    }

    @Test
    @DisplayName("Mixture of Types Case")
    void testMixtureOfTypes() {
        assertEquals(Map.of("1", 1, 1, 1), Task3.freqDict(List.of("1", 1)));
    }
}
