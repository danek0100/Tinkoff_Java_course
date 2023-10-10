package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task6Test {

    @Test
    @DisplayName("Test with numbers that should reach 6174")
    public void testCountK_RegularCases() {
        assertThat(Task6.countK(3524)).isEqualTo(3);
        assertThat(Task6.countK(6621)).isEqualTo(5);
        assertThat(Task6.countK(6554)).isEqualTo(4);
        assertThat(Task6.countK(1234)).isEqualTo(3);
    }

    @Test
    @DisplayName("Test with edge values")
    public void testCountK_EdgeCases() {
        // 6174 should return 0 as it's already the Kaprekar's constant
        assertThat(Task6.countK(6174)).isEqualTo(0);

        // 1001 should return 4 steps
        assertThat(Task6.countK(1001)).isEqualTo(4);

        // 1001 should return 4 steps
        assertThat(Task6.countK(9998)).isEqualTo(5);
    }

    @Test
    @DisplayName("Test with invalid numbers")
    public void testCountK_InvalidCases() {
        // Numbers with all same digits
        assertThat(Task6.countK(1111)).isEqualTo(-1);

        // Numbers that are not 4 digits
        assertThat(Task6.countK(123)).isEqualTo(-1);
        assertThat(Task6.countK(12345)).isEqualTo(-1);
    }

}
