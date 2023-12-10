package edu.project4.utils;

import edu.project4.components.FractalImage;
import edu.project4.components.IFractalImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageUtilsTest {

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("test_image", ".png");
    }

    @AfterEach
    public void tearDown() {
        tempFile.toFile().delete();
    }

    @Test
    public void save_shouldCreateFile() throws IOException {
        IFractalImage image = FractalImage.create(10, 10);
        ImageFormat format = ImageFormat.PNG;

        ImageUtils.save(image, tempFile, format);

        File savedFile = tempFile.toFile();
        assertThat(savedFile.exists()).isTrue();
        assertThat(savedFile.length()).isGreaterThan(0);
    }
}
