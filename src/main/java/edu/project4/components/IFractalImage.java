package edu.project4.components;

/**
 * This interface represents a fractal image.
 */
public interface IFractalImage {

    /**
     * Create a new fractal image with the specified width and height.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A new instance of IFractalImage.
     */
    static IFractalImage create(int width, int height) {
        return null;
    }

    /**
     * Check if the image contains a pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return True if the image contains a pixel at the given coordinates, false otherwise.
     */
    boolean contains(int x, int y);

    /**
     * Get the pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Pixel object at the given coordinates.
     */
    Pixel pixel(int x, int y);

    /**
     * Update the pixel at the specified coordinates with a new pixel.
     *
     * @param x        The x-coordinate of the pixel.
     * @param y        The y-coordinate of the pixel.
     * @param newPixel The new Pixel object to be set at the given coordinates.
     */
    void updatePixel(int x, int y, Pixel newPixel);

    /**
     * Get the width of the image.
     *
     * @return The width of the image.
     */
    int getWidth();

    /**
     * Get the height of the image.
     *
     * @return The height of the image.
     */
    int getHeight();

    /**
     * Get an array of Pixels representing the data of the image.
     *
     * @return An array of Pixels representing the image data.
     */
    Pixel[] getData();
}
