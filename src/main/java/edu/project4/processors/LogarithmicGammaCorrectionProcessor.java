package edu.project4.processors;

import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;

public class LogarithmicGammaCorrectionProcessor implements ImageProcessor {

    private final double gamma;

    public LogarithmicGammaCorrectionProcessor(double gamma) {
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
        double correctedR = Math.max(pixel.r(), 1);
        double correctedG = Math.max(pixel.g(), 1);
        double correctedB = Math.max(pixel.b(), 1);

        double newR = 255 * Math.log(correctedR) / Math.log(1 << 8);
        double newG = 255 * Math.log(correctedG) / Math.log(1 << 8);
        double newB = 255 * Math.log(correctedB) / Math.log(1 << 8);

        newR = Math.pow(newR / 255, gamma) * 255;
        newG = Math.pow(newG / 255, gamma) * 255;
        newB = Math.pow(newB / 255, gamma) * 255;

        int finalR = (int) Math.min(255, Math.max(0, newR));
        int finalG = (int) Math.min(255, Math.max(0, newG));
        int finalB = (int) Math.min(255, Math.max(0, newB));

        return new Pixel(finalR, finalG, finalB, pixel.hitCount());
    }

}
