package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Task2}.
 */
class Task2Test {

    @Test
    @DisplayName("findFridays13InYear - years with Fridays 13th")
    void testFindFridays13InYear() {
        List<LocalDate> expected1925 = Arrays.asList(
            LocalDate.of(1925, 2, 13),
            LocalDate.of(1925, 3, 13),
            LocalDate.of(1925, 11, 13)
        );
        assertEquals(expected1925, Task2.findFridays13InYear(1925));

        List<LocalDate> expected2024 = Arrays.asList(
            LocalDate.of(2024, 9, 13),
            LocalDate.of(2024, 12, 13)
        );
        assertEquals(expected2024, Task2.findFridays13InYear(2024));
    }

    @Test
    @DisplayName("findFridays13InYear - invalid year")
    void testFindFridays13InYearWithInvalidYear() {
        assertThrows(IllegalArgumentException.class, () -> Task2.findFridays13InYear(LocalDate.MIN.getYear() - 1));

        assertThrows(IllegalArgumentException.class, () -> Task2.findFridays13InYear(LocalDate.MAX.getYear() + 1));
    }

    @Test
    @DisplayName("findNextFriday13 - valid dates")
    void testFindNextFriday13() {
        assertEquals(LocalDate.of(2023, 1, 13), Task2.findNextFriday13(LocalDate.of(2023, 1, 1)));
        assertEquals(LocalDate.of(2022, 5, 13), Task2.findNextFriday13(LocalDate.of(2022, 3, 14)));
    }

    @Test
    @DisplayName("findNextFriday13 - starting from Friday 13th")
    void testFindNextFriday13StartingFromFriday13() {
        LocalDate nextFriday13 = Task2.findNextFriday13(LocalDate.of(2023, 1, 13));
        assertTrue(nextFriday13.isAfter(LocalDate.of(2023, 1, 13)));
        assertEquals(DayOfWeek.FRIDAY, nextFriday13.getDayOfWeek());
        assertEquals(13, nextFriday13.getDayOfMonth());
    }

    @Test
    @DisplayName("findNextFriday13 - null date")
    void testFindNextFriday13WithNullDate() {
        assertThrows(IllegalArgumentException.class, () -> Task2.findNextFriday13(null));
    }
}
