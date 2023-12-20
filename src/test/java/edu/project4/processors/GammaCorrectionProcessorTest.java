package edu.project4.processors;

import edu.project4.components.Color;
import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.SyncFractalImage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GammaCorrectionProcessorTest {

    @Test
    public void process_shouldApplyGammaCorrection() {
        double gamma = 2.0;
        GammaCorrectionProcessor processor = new GammaCorrectionProcessor(gamma);
        IFractalImage image = createTestImage();

        int expectedRed = (int) (255 * Math.pow(image.pixel(0, 0).color().r() / 255.0, gamma));
        int expectedGreen = (int) (255 * Math.pow(image.pixel(0, 0).color().g() / 255.0, gamma));
        int expectedBlue = (int) (255 * Math.pow(image.pixel(0, 0).color().b() / 255.0, gamma));

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
}
