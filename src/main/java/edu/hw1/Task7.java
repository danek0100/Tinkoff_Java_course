package edu.hw1;

import java.util.Arrays;

public class Task7 {

    /**
     * Rotates the bits of the given integer to the left.
     *
     * @param n         The integer whose bits are to be rotated.
     * @param shift     The number of positions to rotate the bits.
     * @param bitLength The bit length of the number type.
     * @return The integer with its bits rotated to the left.
     */
    public static int rotateLeft(int n, int shift, int bitLength) {
        int mask = (1 << bitLength) - 1;
        n &= mask;
        shift = shift % bitLength;
        return ((n << shift) & mask) | ((n >>> (bitLength - shift)) & mask);
    }

    /**
     * Rotates the bits of the given integer to the right.
     *
     * @param n         The integer whose bits are to be rotated.
     * @param shift     The number of positions to rotate the bits.
     * @param bitLength The bit length of the number type.
     * @return The integer with its bits rotated to the right.
     */
    public static int rotateRight(int n, int shift, int bitLength) {
        int mask = (1 << bitLength) - 1;
        n &= mask;
        shift = shift % bitLength;
        return ((n >>> shift) & mask) | ((n << (bitLength - shift)) & mask);
    }

    private Task7() {}
}
