package edu.hw1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Task1 class.
 */
public class Task1Test {

    @Test
    @DisplayName("minutesToSeconds - valid input")
    public void testGetVideoDuration_ValidInput() {
        assertThat(Task1.minutesToSeconds("01:00")).isEqualTo(60);
        assertThat(Task1.minutesToSeconds("13:56")).isEqualTo(836);
        assertThat(Task1.minutesToSeconds("999:59")).isEqualTo(59999);
        assertThat(Task1.minutesToSeconds("5:09")).isEqualTo(309);
        assertThat(Task1.minutesToSeconds("5:9")).isEqualTo(309);
    }

    @Test
    @DisplayName("minutesToSeconds - invalid input")
    public void testGetVideoDuration_InvalidInput() {
        assertThat(Task1.minutesToSeconds("10:60")).isEqualTo(-1);
        assertThat(Task1.minutesToSeconds("abc:de")).isEqualTo(-1);
        assertThat(Task1.minutesToSeconds("13:")).isEqualTo(-1);
        assertThat(Task1.minutesToSeconds(":56")).isEqualTo(-1);
        assertThat(Task1.minutesToSeconds("::")).isEqualTo(-1);
    }
}
