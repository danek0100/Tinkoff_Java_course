package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Task2 class.
 */
public class Task2Test {

    @Test
    public void testGetDigitsNumber_BasicCases() {
        assertThat(Task2.countDigits(4666)).isEqualTo(4);
        assertThat(Task2.countDigits(544)).isEqualTo(3);
        assertThat(Task2.countDigits(0)).isEqualTo(1);
        assertThat(Task2.countDigits(9)).isEqualTo(1);
        assertThat(Task2.countDigits(10)).isEqualTo(2);
    }

    @Test
    public void testGetDigitsNumber_NegativeNumbers() {
        assertThat(Task2.countDigits(-4666)).isEqualTo(4);
        assertThat(Task2.countDigits(-544)).isEqualTo(3);
        assertThat(Task2.countDigits(-1)).isEqualTo(1);
    }

    @Test
    public void testGetDigitsNumber_EdgeCases() {
        assertThat(Task2.countDigits(Long.MAX_VALUE)).isEqualTo(19); // Max long value is 19 digits
        assertThat(Task2.countDigits(Long.MIN_VALUE) + 1).isEqualTo(19); // Min long value is also 19 digits (ignoring the negative sign)
        assertThat(Task2.countDigits(Long.MIN_VALUE)).isEqualTo(1); // Min long value is also 19 digits (ignoring the negative sign)
    }
}
