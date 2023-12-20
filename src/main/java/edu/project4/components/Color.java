package edu.project4.components;

import java.util.Random;

/**
 * Represents an RGB color with red, green, and blue components.
 * Each component is an integer value between 0 and 255, inclusive.
 */
public record Color(int r, int g, int b) {
    private final static int BOUND = 256;

    /**
     * Generates a random RGB color.
     *
     * @return A new {@code Color} object with each of its RGB components
     *         randomly chosen between 0 (inclusive) and 256 (exclusive).
     */
    public static Color generate() {
        Random random = new Random();

        int r = random.nextInt(0, BOUND);
        int g = random.nextInt(0, BOUND);
        int b = random.nextInt(0, BOUND);

        return new Color(r, g, b);
    }
}
