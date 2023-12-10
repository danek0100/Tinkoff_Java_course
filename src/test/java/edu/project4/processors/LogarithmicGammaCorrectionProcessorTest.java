package edu.project4.processors;

import edu.project4.components.Color;
import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.SyncFractalImage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogarithmicGammaCorrectionProcessorTest {

    @Test
    public void process_shouldApplyLogarithmicGammaCorrection() {
        double gamma = 2.0;
        LogarithmicGammaCorrectionProcessor processor = new LogarithmicGammaCorrectionProcessor(gamma);
        IFractalImage image = createTestImage();

        Pixel originalPixel = image.pixel(0, 0);
        int expectedRed = calculateLogGammaCorrection(originalPixel.color().r(), gamma);
        int expectedGreen = calculateLogGammaCorrection(originalPixel.color().g(), gamma);
        int expectedBlue = calculateLogGammaCorrection(originalPixel.color().b(), gamma);

        processor.process(image);

        Pixel processedPixel = image.pixel(0, 0);
        assertThat(processedPixel.color().r()).isEqualTo(expectedRed);
        assertThat(processedPixel.color().g()).isEqualTo(expectedGreen);
        assertThat(processedPixel.color().b()).isEqualTo(expectedBlue);
    }

    private IFractalImage createTestImage() {
        SyncFractalImage image = SyncFractalImage.create(1, 1);
        image.updatePixel(0, 0, new Pixel(new Color(50, 100, 150), 1));
        return image;
    }

    private int calculateLogGammaCorrection(int colorValue, double gamma) {
        double correctedValue = Math.max(colorValue, 1);
        double boarder = 255.0;
        double corr = 8.0;

        double logValue = boarder * Math.log(correctedValue) / Math.log(1 << (int) corr);
        double gammaCorrectedValue = Math.pow(logValue / boarder, gamma) * boarder;

        return (int) Math.min(boarder, Math.max(0, gammaCorrectedValue));
    }
}
