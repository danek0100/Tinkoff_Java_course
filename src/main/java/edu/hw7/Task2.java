package edu.hw7;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * The {@code Task2} class provides a method for calculating the factorial of a number
 * using parallel processing techniques.
 */
public class Task2 {

    /**
     * Calculates the factorial of a given number {@code n} using a parallel stream.
     *
     * @param n the number to calculate the factorial of. It should be a non-negative integer.
     * @return the factorial of {@code n} as a {@code BigInteger}.
     * @throws IllegalArgumentException if {@code n} is negative.
     */
    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }

        return IntStream.rangeClosed(1, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }

    private Task2() {}
}
