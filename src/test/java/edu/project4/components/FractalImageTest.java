package edu.project4.components;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FractalImageTest {

    @Test
    public void create_shouldInitializeAllPixelsToBlack() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);

        for (Pixel pixel : image.getData()) {
            assertThat(pixel.color()).isEqualTo(new Color(0, 0, 0));
            assertThat(pixel.hitCount()).isZero();
        }
    }

    @Test
    public void contains_shouldReturnTrueForValidCoordinates() {
        FractalImage image = FractalImage.create(10, 10);
        assertThat(image.contains(5, 5)).isTrue();
    }

    @Test
    public void contains_shouldReturnFalseForInvalidCoordinates() {
        FractalImage image = FractalImage.create(10, 10);
        assertThat(image.contains(10, 10)).isFalse(); // Out of bounds
    }

    @Test
    public void pixel_shouldReturnCorrectPixel() {
        FractalImage image = FractalImage.create(10, 10);
        image.updatePixel(5, 5, new Pixel(new Color(255, 255, 255), 1));

        Pixel pixel = image.pixel(5, 5);
        assertThat(pixel.color()).isEqualTo(new Color(255, 255, 255));
        assertThat(pixel.hitCount()).isEqualTo(1);
    }

    @Test
    public void pixel_shouldReturnNullForInvalidCoordinates() {
        FractalImage image = FractalImage.create(10, 10);
        assertNull(image.pixel(10, 10));
    }

    @Test
    public void updatePixel_shouldCorrectlyUpdatePixel() {
        FractalImage image = FractalImage.create(10, 10);
        image.updatePixel(5, 5, new Pixel(new Color(123, 45, 67), 2));

        Pixel updatedPixel = image.pixel(5, 5);
        assertThat(updatedPixel.color()).isEqualTo(new Color(123, 45, 67));
        assertThat(updatedPixel.hitCount()).isEqualTo(2);
    }
}
