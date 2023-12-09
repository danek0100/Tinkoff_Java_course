package edu.project4.processors;

import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;

public class GammaCorrectionProcessor implements ImageProcessor {

    private final double gamma;

    public GammaCorrectionProcessor(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        for (int i = 0; i < image.data().length; i++) {
            Pixel pixel = image.data()[i];
            image.data()[i] = applyGammaCorrection(pixel);
        }
    }

    private Pixel applyGammaCorrection(Pixel pixel) {
        int r = (int)(255 * Math.pow((double)pixel.r() / 255, gamma));
        int g = (int)(255 * Math.pow((double)pixel.g() / 255, gamma));
        int b = (int)(255 * Math.pow((double)pixel.b() / 255, gamma));

        return new Pixel(r, g, b, pixel.hitCount());
    }
}
