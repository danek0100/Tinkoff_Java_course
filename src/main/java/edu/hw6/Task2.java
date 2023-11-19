package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The {@code Task2} class provides a method for cloning a file in its directory with a new name.
 * It simulates the behavior of file copying in Windows Explorer, where copies of a file
 * are renamed with a suffix to distinguish them from the original file.
 */
public class Task2 {

    private static final int COPY_LIMIT = 100;

    /**
     * Clones the specified file in its directory with a new name.
     * The new file name is based on the original file name, appending " — копия" for the first copy,
     * and " — копия (n)" for subsequent copies, where n is the copy number.
     * The method ensures that the new file name does not conflict with existing files.
     *
     * @param path The path of the file to be cloned.
     * @throws IOException If the file does not exist, or if an I/O error occurs during the copying.
     *                     Also throws an exception if the number of copies reaches a limit (e.g., 1000 copies).
     */
    public static void cloneFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("File doesn't exists: " + path);
        }

        String originalFileName = path.getFileName().toString();
        Path directory = path.getParent();

        int dotIndex = originalFileName.lastIndexOf(".");
        String fileNameWithoutExtension = (dotIndex > 0) ? originalFileName.substring(0, dotIndex) : originalFileName;
        String extension = (dotIndex > 0) ? originalFileName.substring(dotIndex) : "";

        Path newPath;
        int copyNumber = 0;
        do {
            copyNumber++;
            StringBuilder newFileNameBuilder = new StringBuilder(fileNameWithoutExtension);
            newFileNameBuilder.append(" — копия");
            if (copyNumber > 1) {
                newFileNameBuilder.append(" (").append(copyNumber).append(")");
            }
            newFileNameBuilder.append(extension);
            newPath = directory.resolve(newFileNameBuilder.toString());

            if (copyNumber == COPY_LIMIT) {
                throw new IOException("Copy limit reached for the file: " + path);
            }
        } while (Files.exists(newPath));

        Files.copy(path, newPath);
    }

    private Task2() {}
}
