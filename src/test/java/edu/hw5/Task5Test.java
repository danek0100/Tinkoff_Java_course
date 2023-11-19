package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for {@link Task5}.
 */
public class Task5Test {

    @Test
    @DisplayName("Valid lprs")
    public void testValidNumberPlates() {
        assertThat(Task5.isValidNumberPlate("А123ВЕ777")).isTrue();
        assertThat(Task5.isValidNumberPlate("О777ОО177")).isTrue();
    }

    @Test
    @DisplayName("Invalid lprs")
    public void testInvalidNumberPlates() {
        assertThat(Task5.isValidNumberPlate("123АВЕ777")).isFalse();
        assertThat(Task5.isValidNumberPlate("А123ВГ77")).isFalse();
        assertThat(Task5.isValidNumberPlate("А123ВЕ7777")).isFalse();
    }

    @Test
    @DisplayName("Empty and Null lprs")
    public void testEmptyAndNullNumberPlates() {
        assertThat(Task5.isValidNumberPlate("")).isFalse();
        assertThatThrownBy(() -> Task5.isValidNumberPlate(null))
            .isInstanceOf(NullPointerException.class);
    }
}
