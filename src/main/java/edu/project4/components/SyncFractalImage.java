package edu.project4.components;

/**
 * A synchronized implementation of the IFractalImage interface that provides thread-safe access to pixel data.
 */
public record SyncFractalImage(Pixel[] data, int width, int height, Object[] locks) implements IFractalImage {

    /**
     * Create a new synchronized fractal image with the specified width and height.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A new instance of SyncFractalImage.
     */
    public static SyncFractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(new Color(0, 0, 0), 0);
        }

        Object[] locks = new Object[width * height];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
        return new SyncFractalImage(data, width, height, locks);
    }

    /**
     * Checks whether the specified coordinates are within the bounds of the image.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are within the image bounds, false otherwise.
     */
    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Retrieves the pixel at the specified coordinates while ensuring thread safety.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Pixel object at the given coordinates, or null if the coordinates are out of bounds.
     */
    public Pixel pixel(int x, int y) {
        if (contains(x, y)) {
            synchronized (locks[y * width + x]) {
                return data[y * width + x];
            }
        }
        return null;
    }

    /**
     * Updates the pixel at the specified coordinates with a new pixel while ensuring thread safety.
     *
     * @param x        The x-coordinate of the pixel.
     * @param y        The y-coordinate of the pixel.
     * @param newPixel The new Pixel object to be set at the given coordinates.
     */
    public void updatePixel(int x, int y, Pixel newPixel) {
        if (contains(x, y)) {
            synchronized (locks[y * width + x]) {
                data[y * width + x] = newPixel;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Pixel[] getData() {
        return data;
    }
}
