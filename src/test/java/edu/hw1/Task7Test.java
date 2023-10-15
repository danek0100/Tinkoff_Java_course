package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task7Test {

    // Tests for rotating bits to the left
    @Test
    @DisplayName("Rotate bits of an integer to the left: Basic Cases")
    public void testRotateLeftBasic() {
        assertThat(Task7.rotateLeft(16, 1, 5)).isEqualTo(1);
        assertThat(Task7.rotateLeft(17, 2, 5)).isEqualTo(6);
    }

    @Test
    @DisplayName("Rotate bits of an integer to the left: Different Bit Lengths")
    public void testRotateLeftDifferentBitLengths() {
        assertThat(Task7.rotateLeft(2, 1, 2)).isEqualTo(1);
        assertThat(Task7.rotateLeft(8, 2, 4)).isEqualTo(2);
        assertThat(Task7.rotateLeft(65311, 3, 16)).isEqualTo(63743); // 1111 1111 0001 1111 --> 1111 1000 1111 1111 (63743)
    }

    @Test
    @DisplayName("Rotate bits of an integer to the left: Extreme Cases")
    public void testRotateLeftExtremeCases() {
        assertThat(Task7.rotateLeft(0, 2, 5)).isEqualTo(0);
        assertThat(Task7.rotateLeft(31, 2, 5)).isEqualTo(31);
    }

    // Tests for rotating bits to the right
    @Test
    @DisplayName("Rotate bits of an integer to the right: Basic Cases")
    public void testRotateRightBasic() {
        assertThat(Task7.rotateRight(8, 1, 5)).isEqualTo(4);
        assertThat(Task7.rotateRight(1, 1, 5)).isEqualTo(16);
    }

    @Test
    @DisplayName("Rotate bits of an integer to the right: Different Bit Lengths")
    public void testRotateRightDifferentBitLengths() {
        assertThat(Task7.rotateRight(1, 1, 2)).isEqualTo(2);
        assertThat(Task7.rotateRight(2, 2, 4)).isEqualTo(8);
        assertThat(Task7.rotateRight(65311, 3, 16)).isEqualTo(65507); // 1111 1111 0001 1111 --> 1111 1111 1110 0011 (65507)
    }

    @Test
    @DisplayName("Rotate bits of an integer to the right: Extreme Cases")
    public void testRotateRightExtremeCases() {
        assertThat(Task7.rotateRight(0, 2, 5)).isEqualTo(0);
        assertThat(Task7.rotateRight(31, 2, 5)).isEqualTo(31);
    }
}
