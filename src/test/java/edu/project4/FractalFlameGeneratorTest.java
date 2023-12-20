package edu.project4;

import edu.project4.components.AffineTransformation;
import edu.project4.config.Config;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ImageFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import static org.assertj.core.api.Assertions.assertThat;

public class FractalFlameGeneratorTest {

    @TempDir
    Path tempDir;

    @AfterEach public void clean() {
        tempDir.toFile().delete();
    }

    @Test
    public void generateAndSaveFractal_shouldCreateFile() throws IOException {
        Config config = new Config(
            100, 100, -1, 1, -1, 1, 1, 2, 100, 100, 1,
            ImageFormat.PNG, true, new AffineTransformation[]{
                new AffineTransformation(-0.69, 0.006, 0, -0.616, 0.51, 0, 150, 30, 5)},
            tempDir.toString(), "test_fractal", new Transformation[]{ new DiskTransformation() },
            2.2, 42
        );

        FractalFlameGenerator.generateAndSaveFractal(config);

        Path savedFile = tempDir.resolve("test_fractal.png");
        assertThat(Files.exists(savedFile)).isTrue();
        assertThat(Files.size(savedFile)).isGreaterThan(0);
    }
}
