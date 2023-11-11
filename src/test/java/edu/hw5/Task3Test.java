package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Task3}.
 */
public class Task3Test {

    @Test
    @DisplayName("Parse standard ISO date")
    public void testParseStandardIsoDate() {
        Optional<LocalDate> date = Task3.parseDate("2020-10-10");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.of(2020, 10, 10), date.get());
    }

    @Test
    @DisplayName("Parse date with no leading zeros")
    public void testParseDateWithNoLeadingZeros() {
        Optional<LocalDate> date = Task3.parseDate("2020-12-2");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.of(2020, 12, 2), date.get());
    }

    @Test
    @DisplayName("Parse US format date with slashes")
    public void testParseUsFormatDateWithSlashes() {
        Optional<LocalDate> date = Task3.parseDate("1/3/1976");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.of(1976, 1, 3), date.get());
    }

    @Test
    @DisplayName("Parse date with two-digit year")
    public void testParseDateWithTwoDigitYear() {
        Optional<LocalDate> date = Task3.parseDate("1/3/20");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.of(2020, 1, 3), date.get());
    }

    @Test
    @DisplayName("Parse relative date 'tomorrow'")
    public void testParseRelativeDateTomorrow() {
        Optional<LocalDate> date = Task3.parseDate("tomorrow");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.now().plusDays(1), date.get());
    }

    @Test
    @DisplayName("Parse relative date 'today'")
    public void testParseRelativeDateToday() {
        Optional<LocalDate> date = Task3.parseDate("today");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.now(), date.get());
    }

    @Test
    @DisplayName("Parse relative date 'yesterday'")
    public void testParseRelativeDateYesterday() {
        Optional<LocalDate> date = Task3.parseDate("yesterday");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.now().minusDays(1), date.get());
    }

    @Test
    @DisplayName("Parse '1 day ago'")
    public void testParseOneDayAgo() {
        Optional<LocalDate> date = Task3.parseDate("1 day ago");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.now().minusDays(1), date.get());
    }

    @Test
    @DisplayName("Parse '2234 days ago'")
    public void testParseManyDaysAgo() {
        Optional<LocalDate> date = Task3.parseDate("2234 days ago");
        assertTrue(date.isPresent());
        assertEquals(LocalDate.now().minusDays(2234), date.get());
    }

    @Test
    @DisplayName("Return empty for non-date string")
    public void testReturnEmptyForNonDateString() {
        Optional<LocalDate> date = Task3.parseDate("not a date");
        assertTrue(date.isEmpty());
    }
}
