package edu.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

/**
 * A recursive task for searching files in a file system that match a given predicate.
 * This class extends RecursiveTask and is intended for use with a ForkJoinPool
 * to enable efficient parallel processing of large file system structures.
 */
public class Task2FileSearchTask extends RecursiveTask<List<File>> {

    private final File directory;
    private final Predicate<File> predicate;

    /**
     * Constructs a new Task2FileSearchTask for the specified directory and predicate.
     *
     * @param directory The directory to search in.
     * @param predicate The predicate to apply to each file. Files that match the predicate
     *                  will be included in the result list.
     */
    public Task2FileSearchTask(File directory, Predicate<File> predicate) {
        this.directory = directory;
        this.predicate = predicate;
    }

    /**
     * Executes the task to search for files matching the predicate. The task recursively
     * searches through the directory structure, creating subtasks for each subdirectory.
     * Files that match the predicate are added to the result list.
     *
     * @return A list of files that match the predicate in the given directory and its subdirectories.
     */
    @Override
    protected List<File> compute() {
        List<File> matchingFiles = new ArrayList<>();
        List<Task2FileSearchTask> tasks = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    Task2FileSearchTask task = new Task2FileSearchTask(file, predicate);
                    task.fork();
                    tasks.add(task);
                } else {
                    if (predicate.test(file)) {
                        matchingFiles.add(file);
                    }
                }
            }
        }

        for (Task2FileSearchTask task : tasks) {
            matchingFiles.addAll(task.join());
        }

        return matchingFiles;
    }
}
