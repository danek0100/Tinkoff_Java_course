package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {

    @Test
    @DisplayName("Factorial of Zero")
    void testFactorialOfZero() {
        assertEquals(BigInteger.ONE, Task2.factorial(0), "Factorial of 0 should be 1");
    }

    @Test
    @DisplayName("Factorial of One")
    void testFactorialOfOne() {
        assertEquals(BigInteger.ONE, Task2.factorial(1), "Factorial of 1 should be 1");
    }

    @Test
    @DisplayName("Factorial of a Positive Number")
    void testFactorialOfPositiveNumber() {
        BigInteger expected = new BigInteger("120");
        assertEquals(expected, Task2.factorial(5), "Factorial of 5 should be 120");
    }

    @Test
    @DisplayName("Factorial of Large Number")
    void testFactorialOfLargeNumber() {
        BigInteger expected = new BigInteger("2432902008176640000");
        assertEquals(expected, Task2.factorial(20), "Factorial of 20 should be 2432902008176640000");
    }

    @Test
    @DisplayName("Factorial of Negative Number Throws Exception")
    void testFactorialOfNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> Task2.factorial(-1),
            "Factorial of a negative number should throw IllegalArgumentException");
    }
}
