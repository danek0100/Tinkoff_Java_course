package edu.hw6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.Task3.globMatches;
import static edu.hw6.Task3.largerThan;
import static edu.hw6.Task3.magicNumber;
import static edu.hw6.Task3.IS_READABLE;
import static edu.hw6.Task3.regexContains;
import static edu.hw6.Task3.IS_REGULAR_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    private Path testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        testFilePath = Files.createTempFile("testFile", ".txt");
        Files.writeString(testFilePath, "Test content");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    @DisplayName("Regular File Filter Test")
    public void regularFileFilterTest() throws IOException {
        assertThat(IS_REGULAR_FILE.accept(testFilePath)).isTrue();

        Path testDir = Files.createTempDirectory("testDir");
        assertThat(IS_REGULAR_FILE.accept(testDir)).isFalse();

        Files.deleteIfExists(testDir);
    }

    @Test
    @DisplayName("Readable Filter Test")
    public void readableFilterTest() throws IOException {
        assertThat(IS_READABLE.accept(testFilePath)).isTrue();
    }

    @Test
    @DisplayName("Larger Than Filter Test")
    public void largerThanFilterTest() throws IOException {
        assertThat(largerThan(100_000).accept(testFilePath)).isFalse();

        Path largeFile = Files.createTempFile("largeTestFile", ".txt");
        byte[] largeContent = new byte[11];
        Files.write(largeFile, largeContent);
        assertThat(largerThan(10).accept(largeFile)).isTrue();

        Files.deleteIfExists(largeFile);
    }

    @Test
    @DisplayName("Magic Number Filter Test")
    public void magicNumberFilterTest() throws IOException {
        Path magicFile = Files.createTempFile("magicTestFile", ".bin");
        Files.write(magicFile, new byte[]{(byte) 0x89, 'P', 'N', 'G'});

        assertThat(magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G').accept(magicFile)).isTrue();

        Files.deleteIfExists(magicFile);
    }

    @Test
    @DisplayName("Glob Matches Filter Test")
    public void globMatchesFilterTest() throws IOException {
        assertThat(globMatches("*.txt").accept(testFilePath)).isTrue();

        Path testPngFile = Files.createTempFile("testFile", ".png");
        assertThat(globMatches("*.txt").accept(testPngFile)).isFalse();

        Files.deleteIfExists(testPngFile);
    }

    @Test
    @DisplayName("Regex Contains Filter Test")
    public void regexContainsFilterTest() throws IOException {
        assertThat(regexContains("-").accept(testFilePath)).isFalse();

        Path testFileWithDash = Files.createTempFile("test-File", ".txt");
        assertThat(regexContains("-").accept(testFileWithDash)).isTrue();

        Files.deleteIfExists(testFileWithDash);
    }

    @Test
    @DisplayName("Chain filter test")
    public void chainFilterTest() throws IOException {
        Path validFile = Files.createTempFile("valid-TestFile", ".png");
        Files.write(validFile, new byte[]{(byte) 0x89, 'P', 'N', 'G', 0x0, 0x0, 0x0});

        DirectoryStream.Filter<Path> filter = IS_REGULAR_FILE
            .and(IS_READABLE)
            .and(largerThan(1))
            .and(magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("-"));

        boolean validFileFound = false;
        boolean invalidFileFound = false;
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(testFilePath.getParent(), filter)) {
            for (Path entry : entries) {
                if (entry.equals(testFilePath)) {
                    invalidFileFound = true;
                    break;
                }
            }
        } catch (IOException ignored) {}
        assertThat(invalidFileFound).isFalse();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(validFile.getParent(), filter)) {
            for (Path entry : entries) {
                if (entry.equals(validFile)) {
                    validFileFound = true;
                    break;
                }
            }
        } catch (IOException ignored) {}
        assertThat(validFileFound).isTrue();
    }
}
