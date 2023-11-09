package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        assertEquals("II", Task4.convertToRoman(2));
        assertEquals("XII", Task4.convertToRoman(12));
        assertEquals("XVI", Task4.convertToRoman(16));
    }

    @Test
    @DisplayName("Additional Test Cases")
    void additionalTestCases() {
        assertEquals("I", Task4.convertToRoman(1));
        assertEquals("MMMCMXCIX", Task4.convertToRoman(3999));
        assertEquals("XL", Task4.convertToRoman(40));
        assertEquals("XC", Task4.convertToRoman(90));
        assertEquals("CD", Task4.convertToRoman(400));
        assertEquals("CM", Task4.convertToRoman(900));
        assertEquals("LXXXVIII", Task4.convertToRoman(88));
        assertEquals("CXXIII", Task4.convertToRoman(123));
        assertEquals("MMMMMMMD", Task4.convertToRoman(7500));
    }

}
