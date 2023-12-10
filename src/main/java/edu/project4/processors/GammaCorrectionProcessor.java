package edu.project4.processors;

import edu.project4.components.Color;
import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;

/**
 * Image processor for applying gamma correction to a fractal image.
 */
public class GammaCorrectionProcessor implements ImageProcessor {

    private final double gamma;
    private static final int BORDER = 255;

    /**
     * Constructs a GammaCorrectionProcessor with the specified gamma correction factor.
     *
     * @param gamma The gamma correction factor to be applied to the image.
     */
    public GammaCorrectionProcessor(double gamma) {
        this.gamma = gamma;
    }

    /**
     * Apply gamma correction to the given fractal image.
     *
     * @param image The fractal image to be processed.
     */
    @Override
    public void process(IFractalImage image) {
        for (int i = 0; i < image.getData().length; i++) {
            Pixel pixel = image.getData()[i];
            image.getData()[i] = applyGammaCorrection(pixel);
        }
    }

    private Pixel applyGammaCorrection(Pixel pixel) {
        int r = (int) (BORDER * Math.pow((double) pixel.color().r() / BORDER, gamma));
        int g = (int) (BORDER * Math.pow((double) pixel.color().g() / BORDER, gamma));
        int b = (int) (BORDER * Math.pow((double) pixel.color().b() / BORDER, gamma));

        return new Pixel(new Color(r, g, b), pixel.hitCount());
    }
}
