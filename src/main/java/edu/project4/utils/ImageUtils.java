package edu.project4.utils;

import edu.project4.ImageFormat;
import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

@SuppressWarnings("MagicNumber")
public final class ImageUtils {

    private ImageUtils() {}

    public static void save(FractalImage image, Path filename, ImageFormat format) throws IOException {
        BufferedImage bufferedImage = convertToBufferedImage(image);
        ImageIO.write(bufferedImage, format.toString(), filename.toFile());
    }

    private static BufferedImage convertToBufferedImage(FractalImage fractalImage) {
        BufferedImage bufferedImage = new BufferedImage(fractalImage.width(), fractalImage.height(),
            BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractalImage.height(); y++) {
            for (int x = 0; x < fractalImage.width(); x++) {
                Pixel pixel = fractalImage.pixel(x, y);
                int color = (pixel.r() << 16) | (pixel.g() << 8) | pixel.b();
                bufferedImage.setRGB(x, y, color);
            }
        }

        return bufferedImage;
    }
}
