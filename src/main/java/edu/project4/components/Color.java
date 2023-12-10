package edu.project4.components;

import java.util.Random;

public record Color(int r, int g, int b) {
    private final static int BOUND = 256;

    public static Color generate() {
        Random random = new Random();

        int r = random.nextInt(0, BOUND);
        int g = random.nextInt(0, BOUND);
        int b = random.nextInt(0, BOUND);

        return new Color(r, g, b);
    }
}
