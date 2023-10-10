package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task5Test {

    @Test
    @DisplayName("Test with numbers that have palindrome descendants")
    public void testWithPalindromeDescendants() {
        assertThat(Task5.isPalindromeDescendant(11211230)).isTrue();
        assertThat(Task5.isPalindromeDescendant(13001120)).isTrue();
        assertThat(Task5.isPalindromeDescendant(23336014)).isTrue();
        assertThat(Task5.isPalindromeDescendant(11)).isTrue();
    }

    @Test
    @DisplayName("Test with numbers that do not have palindrome descendants")
    public void testWithNonPalindromeDescendants() {
        assertThat(Task5.isPalindromeDescendant(11211231)).isFalse();
        assertThat(Task5.isPalindromeDescendant(102102)).isFalse();
    }

    @Test
    @DisplayName("Test with edge values")
    public void testWithEdgeValues() {
        // Single-digit numbers are not considered valid for this problem
        assertThat(Task5.isPalindromeDescendant(9)).isFalse();

        // Two-digit numbers that are palindromes
        assertThat(Task5.isPalindromeDescendant(22)).isTrue();

        // Two-digit numbers that are not palindromes
        assertThat(Task5.isPalindromeDescendant(21)).isFalse();
    }
}
