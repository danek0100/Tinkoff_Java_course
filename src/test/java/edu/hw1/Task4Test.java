package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task4Test {

    @Test
    @DisplayName("Test with normal values")
    public void testFixString_BasicCases() {
        assertThat(Task4.fixString("123456")).isEqualTo("214365");
        assertThat(Task4.fixString("hTsii  s aimex dpus rtni.g")).isEqualTo("This is a mixed up string.");
        assertThat(Task4.fixString("badce")).isEqualTo("abcde");
    }

    @Test
    @DisplayName("Test with edge values")
    public void testFixString_EdgeCases() {
        // Test with empty string
        assertThat(Task4.fixString("")).isEqualTo("");

        // Test with single-character string
        assertThat(Task4.fixString("a")).isEqualTo("a");

        // Test with two-character string
        assertThat(Task4.fixString("ab")).isEqualTo("ba");
    }
}
