package edu.hw6;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2Test {
    private Path testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        testFilePath = Files.createTempFile("testFile", ".txt");
        Files.writeString(testFilePath, "Test content");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
        for (int i = 1; i <= 1000; i++) {
            Path copyPath = testFilePath.getParent().resolve(testFilePath.getFileName().toString().replace(".txt", " — копия" + (i == 1 ? "" : " (" + i + ")") + ".txt"));
            Files.deleteIfExists(copyPath);
        }
    }

    @Test
    @DisplayName("Clone file successfully")
    public void testCloneFileSuccess() throws IOException {
        Task2.cloneFile(testFilePath);
        Path clonedFilePath = testFilePath.getParent().resolve(testFilePath.getFileName().toString().replace(".txt", " — копия.txt"));
        assertThat(Files.exists(clonedFilePath)).isTrue();
        assertThat(Files.readString(clonedFilePath)).isEqualTo("Test content");
    }

    @Test
    @DisplayName("Throw IOException when file does not exist")
    public void testCloneFileNotExist() {
        Path nonExistentPath = testFilePath.getParent().resolve("nonExistentFile.txt");
        assertThrows(IOException.class, () -> Task2.cloneFile(nonExistentPath));
    }

    @Test
    @DisplayName("Create multiple clones with correct naming")
    public void testMultipleClones() throws IOException {
        for (int i = 1; i <= 3; i++) {
            Task2.cloneFile(testFilePath);
            Path clonePath = testFilePath.getParent().resolve(testFilePath.getFileName().toString().replace(".txt", " — копия" + (i == 1 ? "" : " (" + i + ")") + ".txt"));
            assertThat(Files.exists(clonePath)).isTrue();
        }
    }

    @Test
    @DisplayName("Handle maximum clone limit")
    public void testCloneLimit() {
        IOException thrown = assertThrows(IOException.class, () -> {
            for (int i = 0; i <= 100; i++) {
                Task2.cloneFile(testFilePath);
            }
        });
        assertThat(thrown.getMessage()).contains("Copy limit reached for the file");
    }
}
