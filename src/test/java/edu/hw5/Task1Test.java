package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link Task1}.
 */
public class Task1Test {

    @Test
    @DisplayName("getAverageSessionTime - valid input")
    public void testGetAverageSessionTime_ValidInput() {
        String[] sessions = {
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        };
        String expected = "3h 40m";
        String actual = Task1.getAverageSessionTime(sessions);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getAverageSessionTime - single session")
    public void testGetAverageSessionTime_SingleSession() {
        String[] sessions = {"2022-03-12, 20:20 - 2022-03-12, 22:20"};
        String expected = "2h 0m";
        String actual = Task1.getAverageSessionTime(sessions);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getAverageSessionTime - empty input")
    public void testGetAverageSessionTime_EmptyInput() {
        String[] sessions = {};
        assertThrows(IllegalArgumentException.class, () -> Task1.getAverageSessionTime(sessions));
    }

    @Test
    @DisplayName("getAverageSessionTime - null input")
    public void testGetAverageSessionTime_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> Task1.getAverageSessionTime(null));
    }

    @Test
    @DisplayName("getAverageSessionTime - invalid date format")
    public void testGetAverageSessionTime_InvalidDateFormat() {
        String[] sessions = {"2022-03-12, 20:20 - 2022/03/12, 22:20"};
        assertThrows(IllegalArgumentException.class, () -> Task1.getAverageSessionTime(sessions));
    }

    @Test
    @DisplayName("getAverageSessionTime - end time before start time")
    public void testGetAverageSessionTime_EndTimeBeforeStartTime() {
        String[] sessions = {"2022-03-12, 22:20 - 2022-03-12, 20:20"};
        assertThrows(IllegalArgumentException.class, () -> Task1.getAverageSessionTime(sessions));
    }
}
