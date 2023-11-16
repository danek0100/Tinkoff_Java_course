package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task4Test {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("File exists")
    public void testFileCreation() throws IOException {
        Path tempFile = tempDir.resolve("newFile.txt");
        String testContent = "Test content";

        Task4.chainWrite(tempFile.toString(), testContent);

        assertThat(Files.exists(tempFile)).isTrue();
    }


    @Test
    @DisplayName("Test chain write")
    public void testChainWrite() throws IOException {
        Path tempFile = tempDir.resolve("testOutput.txt");
        String testContent = "Programming is learned by writing programs. ― Brian Kernighan";

        Task4.chainWrite(tempFile.toString(), testContent);

        String fileContent = Files.readString(tempFile);
        assertThat(fileContent).contains(testContent);
    }
}
