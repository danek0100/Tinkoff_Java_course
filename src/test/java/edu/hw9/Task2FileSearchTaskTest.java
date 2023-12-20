package edu.hw9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import static org.assertj.core.api.Assertions.*;

public class Task2FileSearchTaskTest {

    @TempDir
    Path tempDir;

    @Test
    public void testFileSearchByPredicate() throws IOException {
        Files.createFile(tempDir.resolve("test1.txt"));
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));
        Files.createFile(subDir.resolve("test2.txt"));
        Files.createFile(subDir.resolve("test3.log"));

        Predicate<File> txtFilePredicate = file -> file.getName().endsWith(".txt");

        Task2FileSearchTask task = new Task2FileSearchTask(tempDir.toFile(), txtFilePredicate);
        try (ForkJoinPool pool = new ForkJoinPool()) {
            List<File> txtFiles = pool.invoke(task);
            assertThat(txtFiles).hasSize(2).allMatch(file -> file.getName().endsWith(".txt"));
        }
    }
}
