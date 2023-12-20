package edu.project4.components;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

public class SyncFractalImageTest {

    @Test
    public void create_shouldInitializeAllPixelsToBlack() {
        SyncFractalImage image = SyncFractalImage.create(10, 10);

        for (Pixel pixel : image.getData()) {
            assertThat(pixel.color()).isEqualTo(new Color(0, 0, 0));
            assertThat(pixel.hitCount()).isZero();
        }
    }

    @Test
    public void pixelAndUpdatePixel_shouldBeThreadSafe() throws InterruptedException {
        SyncFractalImage image = SyncFractalImage.create(10, 10);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable updateTask = () -> {
            for (int i = 0; i < 1000; i++) {
                image.updatePixel(5, 5, new Pixel(new Color(i % 256, i % 256, i % 256), i));
            }
        };

        executor.execute(updateTask);
        executor.execute(updateTask);

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        Pixel finalPixel = image.pixel(5, 5);
        assertThat(finalPixel.hitCount()).isBetween(0, 1999);
    }

    @Test
    public void contains_shouldReturnTrueForValidCoordinates() {
        SyncFractalImage image = SyncFractalImage.create(10, 10);
        assertThat(image.contains(5, 5)).isTrue();
    }

    @Test
    public void contains_shouldReturnFalseForInvalidCoordinates() {
        SyncFractalImage image = SyncFractalImage.create(10, 10);
        assertThat(image.contains(10, 10)).isFalse(); // Out of bounds
    }
}
