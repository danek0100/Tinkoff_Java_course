package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link Task7}.
 */
public class Task7Test {

    @Test
    @DisplayName("Third char is zero")
    public void testHasThirdCharAsZero() {
        assertThat(Task7.hasThirdCharAsZero("110")).isTrue();
        assertThat(Task7.hasThirdCharAsZero("001")).isFalse();
        assertThat(Task7.hasThirdCharAsZero("0110")).isFalse();
        assertThat(Task7.hasThirdCharAsZero("100")).isTrue();
        assertThat(Task7.hasThirdCharAsZero("00")).isFalse();
    }

    @Test
    @DisplayName("Start and end are equals")
    public void testStartsAndEndsWithSameChar() {
        assertThat(Task7.startsAndEndsWithSameChar("1001")).isTrue();
        assertThat(Task7.startsAndEndsWithSameChar("0110")).isTrue();
        assertThat(Task7.startsAndEndsWithSameChar("1")).isTrue();
        assertThat(Task7.startsAndEndsWithSameChar("100")).isFalse();
        assertThat(Task7.startsAndEndsWithSameChar("10")).isFalse();
    }

    @Test
    @DisplayName("Length 1-3")
    public void testIsLengthOneToThree() {
        assertThat(Task7.isLengthOneToThree("1")).isTrue();
        assertThat(Task7.isLengthOneToThree("01")).isTrue();
        assertThat(Task7.isLengthOneToThree("101")).isTrue();
        assertThat(Task7.isLengthOneToThree("1000")).isFalse();
        assertThat(Task7.isLengthOneToThree("")).isFalse();
    }
}
