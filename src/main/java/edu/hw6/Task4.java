package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

/**
 * This class provides a method to write text to a file using a chain of OutputStreams.
 */
public class Task4 {

    /**
     * Writes the provided text to the specified file path using a chain of OutputStreams.
     * The chain includes CheckedOutputStream for checksum, BufferedOutputStream for buffering,
     * OutputStreamWriter for character encoding, and PrintWriter for text writing.
     *
     * @param path The file path to write to.
     * @param text The text to be written to the file.
     * @throws IOException If an I/O error occurs.
     */
    public static void chainWrite(String path, String text) throws IOException {
        Path filePath = Paths.get(path);

        // Use a CRC32 checksum for the CheckedOutputStream
        Checksum checksum = new CRC32();

        try (OutputStream fileOut = Files.newOutputStream(filePath);
             CheckedOutputStream checkedOut = new CheckedOutputStream(fileOut, checksum);
             BufferedOutputStream bufferedOut = new BufferedOutputStream(checkedOut);
             OutputStreamWriter writer = new OutputStreamWriter(bufferedOut, StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(writer)) {

            printWriter.println(text);
        }
    }

    private Task4() {}
}
