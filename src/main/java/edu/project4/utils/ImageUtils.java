package edu.project4.utils;

import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Utility class for working with fractal image conversion and saving.
 */
@SuppressWarnings("MagicNumber")
public final class ImageUtils {

    private ImageUtils() {}

    /**
     * Save the given fractal image to a file with the specified format.
     *
     * @param image    The fractal image to save.
     * @param filename The path to the file where the image will be saved.
     * @param format   The image format to use (e.g., PNG, JPEG).
     * @throws IOException If an I/O error occurs during saving.
     */
    public static void save(IFractalImage image, Path filename, ImageFormat format) throws IOException {
        BufferedImage bufferedImage = convertToBufferedImage(image);
        ImageIO.write(bufferedImage, format.toString(), filename.toFile());
    }

    /**
     * Convert a fractal image to a BufferedImage.
     *
     * @param fractalImage The fractal image to convert.
     * @return A BufferedImage representation of the fractal image.
     */
    private static BufferedImage convertToBufferedImage(IFractalImage fractalImage) {
        BufferedImage bufferedImage = new BufferedImage(fractalImage.getWidth(), fractalImage.getHeight(),
            BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractalImage.getHeight(); y++) {
            for (int x = 0; x < fractalImage.getWidth(); x++) {
                Pixel pixel = fractalImage.pixel(x, y);
                int color = (pixel.color().r() << 16) | (pixel.color().g() << 8) | pixel.color().b();
                bufferedImage.setRGB(x, y, color);
            }
        }

        return bufferedImage;
    }
}
