package edu.hw9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import static org.assertj.core.api.Assertions.*;

public class Task2DirectorySearchTaskTest {

    @TempDir
    Path tempDir;

    @Test
    public void testLargeDirectorySearch() throws Exception {
        Path largeDir = Files.createDirectory(tempDir.resolve("largeDir"));
        for (int i = 0; i < 1001; i++) {
            Files.createFile(largeDir.resolve("file" + i + ".txt"));
        }

        Path smallDir = Files.createDirectory(tempDir.resolve("smallDir"));
        Files.createFile(smallDir.resolve("file.txt"));

        Task2DirectorySearchTask task = new Task2DirectorySearchTask(tempDir.toFile(), 1000);
        try (ForkJoinPool pool = new ForkJoinPool()){
            List<File> largeDirectories = pool.invoke(task);

            assertThat(largeDirectories).isNotEmpty();
            assertThat(largeDirectories).contains(largeDir.toFile());
            assertThat(largeDirectories).doesNotContain(smallDir.toFile());
        }
    }
}
