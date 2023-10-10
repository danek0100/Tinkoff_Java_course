package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for the Task3 class.
 */
public class Task3Test {

    @Test
    @DisplayName("Test with normal values")
    public void testIsNestable_BasicCases() {
        assertThat(Task3.isNestable(Arrays.asList(1, 2, 3, 4), Arrays.asList(0, 6))).isTrue();
        assertThat(Task3.isNestable(Arrays.asList(3, 1), Arrays.asList(4, 0))).isTrue();
        assertThat(Task3.isNestable(Arrays.asList(9, 9, 8), Arrays.asList(8, 9))).isFalse();
        assertThat(Task3.isNestable(Arrays.asList(1, 2, 3, 4), Arrays.asList(2, 3))).isFalse();
    }

    @Test
    @DisplayName("Test with empty lists")
    public void testIsNestable_EmptyLists() {
        assertThat(Task3.isNestable(Collections.emptyList(), Arrays.asList(1, 2, 3))).isFalse();
        assertThat(Task3.isNestable(Arrays.asList(1, 2, 3), Collections.emptyList())).isFalse();
        assertThat(Task3.isNestable(Collections.emptyList(), Collections.emptyList())).isFalse();
    }

    @Test
    @DisplayName("Test with null values")
    public void testIsNestable_NullValues() {
        assertThat(Task3.isNestable(null, Arrays.asList(1, 2, 3))).isFalse();
        assertThat(Task3.isNestable(Arrays.asList(1, 2, 3), null)).isFalse();
        assertThat(Task3.isNestable(null, null)).isFalse();
    }

    @Test
    @DisplayName("Test with single-element lists")
    public void testIsNestable_SingleElementLists() {
        assertThat(Task3.isNestable(List.of(1), List.of(2))).isFalse();
        assertThat(Task3.isNestable(List.of(2), List.of(1))).isFalse();
    }
}
