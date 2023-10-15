package edu.hw1;


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
        int maskedN = n & mask;
        int cutShift = shift % bitLength;
        return ((maskedN << cutShift) & mask) | ((maskedN >>> (bitLength - cutShift)) & mask);
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
        int maskedN = n & mask;
        int cutShift = shift % bitLength;
        return ((maskedN >>> cutShift) & mask) | ((maskedN << (bitLength - cutShift)) & mask);
    }

    private Task7() {}
}
