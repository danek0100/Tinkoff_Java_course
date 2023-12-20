package edu.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Task2DirectorySearchTask is a recursive task for searching directories in a file system
 * that contain a number of files exceeding a specified threshold.
 * This class extends RecursiveTask and is designed to be used with a ForkJoinPool to
 * efficiently process large file system structures in parallel.
 */
public class Task2DirectorySearchTask extends RecursiveTask<List<File>> {
    private final File directory;
    private final int recognitionFilesInDir;

    /**
     * Constructs a new Task2DirectorySearchTask for the specified directory.
     *
     * @param directory The directory to search.
     * @param recognitionFilesInDir The threshold number of files. Directories containing
     *                              more files than this number are considered "large"
     *                              and will be included in the result.
     */
    public Task2DirectorySearchTask(File directory, int recognitionFilesInDir) {
        this.directory = directory;
        this.recognitionFilesInDir = recognitionFilesInDir;
    }

    /**
     * Executes the task to search for large directories. The task recursively searches
     * through the directory structure, creating subtasks for each subdirectory.
     * Directories containing more files than the specified threshold are added to the result list.
     *
     * @return A list of directories that contain more files than the recognitionFilesInDir threshold.
     */
    @Override
    protected List<File> compute() {
        List<File> largeDirectories = new ArrayList<>();
        List<Task2DirectorySearchTask> tasks = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            int fileCount = 0;
            for (File file : files) {
                if (file.isDirectory()) {
                    Task2DirectorySearchTask task = new Task2DirectorySearchTask(file, recognitionFilesInDir);
                    task.fork();
                    tasks.add(task);
                } else {
                    fileCount++;
                }
            }

            if (fileCount > recognitionFilesInDir) {
                largeDirectories.add(directory);
            }
        }

        for (Task2DirectorySearchTask task : tasks) {
            largeDirectories.addAll(task.join());
        }

        return largeDirectories;
    }
}
