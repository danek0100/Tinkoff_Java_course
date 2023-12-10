package edu.project4.components;

/**
 * Represents a fractal image using an array of {@link Pixel} objects.
 * This record encapsulates the pixel data along with the image's width and height.
 */
public record FractalImage(Pixel[] data, int width, int height) implements IFractalImage {

    /**
     * Creates a {@code FractalImage} instance with specified dimensions.
     * Initializes all pixels to black (0, 0, 0) with a hit count of 0.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A new instance of {@code FractalImage}.
     */
    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(new Color(0, 0, 0), 0);
        }
        return new FractalImage(data, width, height);
    }

    /**
     * Checks if the specified coordinates are within the bounds of the image.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return {@code true} if the coordinates are within the image bounds; {@code false} otherwise.
     */
    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Retrieves the {@link Pixel} at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The {@link Pixel} at the given coordinates, or {@code null} if the coordinates are out of bounds.
     */
    public Pixel pixel(int x, int y) {
        if (contains(x, y)) {
            return data[y * width + x];
        }
        return null;
    }

    /**
     * Updates the {@link Pixel} at the specified coordinates.
     *
     * @param x        The x-coordinate where the pixel should be updated.
     * @param y        The y-coordinate where the pixel should be updated.
     * @param newPixel The new {@link Pixel} to be set at the specified coordinates.
     */
    public void updatePixel(int x, int y, Pixel newPixel) {
        if (contains(x, y)) {
            data[y * width + x] = newPixel;
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
