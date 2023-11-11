package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link Task6}.
 */
public class Task6Test {

    @Test
    @DisplayName("Valid subsequences")
    public void testConsecutiveSubsequence() {
        assertThat(Task6.isConsecutiveSubsequenceRegex("abc", "achfdbaabgabcaabg")).isTrue();
        assertThat(Task6.isConsecutiveSubsequenceRegex("123", "0a1b2c3123dssd")).isTrue();
    }

    @Test
    @DisplayName("Invalid subsequences")
    public void testNonConsecutiveSubsequence() {
        assertThat(Task6.isConsecutiveSubsequenceRegex("abc", "acb")).isFalse();
        assertThat(Task6.isConsecutiveSubsequenceRegex("abc", "bac")).isFalse();
        assertThat(Task6.isConsecutiveSubsequenceRegex("123", "132")).isFalse();
    }

    @Test
    @DisplayName("Empty subsequences")
    public void testEmptySubsequence() {
        assertThat(Task6.isConsecutiveSubsequenceRegex("", "anything")).isTrue();
        assertThat(Task6.isConsecutiveSubsequenceRegex("", "")).isTrue();
    }

    @Test
    @DisplayName("Empty target")
    public void testEmptyTargetString() {
        assertThat(Task6.isConsecutiveSubsequenceRegex("abc", "")).isFalse();
    }
}
