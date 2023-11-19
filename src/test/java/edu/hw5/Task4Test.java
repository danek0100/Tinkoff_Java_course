package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Task4}.
 */
public class Task4Test {

    @Test
    @DisplayName("Valid passwords with 1 special character")
    public void testValidPasswordWithSpecialCharacter() {
        assertTrue(Task4.isValidPassword("Password#1"));
        assertTrue(Task4.isValidPassword("HelloWorld!"));
        assertTrue(Task4.isValidPassword("2022$Year"));
    }

    @Test
    @DisplayName("Valid passwords with multiple special characters")
    public void testValidPasswordWithMultipleSpecialCharacters() {
        assertTrue(Task4.isValidPassword("P@$$w0rd!"));
    }

    @Test
    @DisplayName("Passwords without special characters")
    public void testInvalidPasswordWithoutSpecialCharacter() {
        assertFalse(Task4.isValidPassword("Password"));
        assertFalse(Task4.isValidPassword("12345678"));
    }

    @Test
    @DisplayName("Empty password")
    public void testEmptyPassword() {
        assertFalse(Task4.isValidPassword(""));
    }

    @Test
    @DisplayName("Null password")
    public void testNullPassword() {
        assertThrows(NullPointerException.class, () -> Task4.isValidPassword(null));
    }
}
