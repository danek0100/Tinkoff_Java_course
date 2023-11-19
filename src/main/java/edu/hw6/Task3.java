package edu.hw6;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Arrays;

/**
 * This class provides a set of static methods to create filters for directory streams.
 * These filters can be combined to create complex criteria for file selection.
 */
public class Task3 {

    private Task3() {}

    public static final AbstractFilter IS_REGULAR_FILE = Files::isRegularFile;
    public static final AbstractFilter IS_READABLE = Files::isReadable;

    /**
     * Creates a filter that checks if the file size is larger than a specified value.
     *
     * @param size The file size to compare against.
     * @return A filter that returns true if the file size is greater than the specified size.
     */
    public static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    /**
     * Creates a filter that checks if the file starts with a specified magic number (signature).
     *
     * @param signature The byte array representing the magic number.
     * @return A filter that returns true if the file starts with the specified magic number.
     */
    public static AbstractFilter magicNumber(byte... signature) {
        return path -> {
            try (InputStream is = Files.newInputStream(path)) {
                byte[] fileSignature = new byte[signature.length];
                if (is.read(fileSignature) != signature.length) {
                    return false;
                }
                return Arrays.equals(signature, fileSignature);
            } catch (IOException e) {
                return false;
            }
        };
    }

    /**
     * Creates a filter that matches files against a glob pattern.
     *
     * @param globPattern The glob pattern to match file names against.
     * @return A filter that returns true if the file name matches the glob pattern.
     */
    public static AbstractFilter globMatches(String globPattern) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + globPattern);
        return path -> matcher.matches(path.getFileName());
    }

    /**
     * Creates a filter that checks if the file name matches a regular expression.
     *
     * @param regex The regular expression to match file names against.
     * @return A filter that returns true if the file name matches the regular expression.
     */
    public static AbstractFilter regexContains(String regex) {
        return path -> path.getFileName().toString().contains(regex);
    }

    /**
     * An interface representing a filter for directory streams.
     * It provides a method to chain multiple filters.
     */
    public interface AbstractFilter extends DirectoryStream.Filter<Path> {

        /**
         * Combines this filter with another filter.
         *
         * @param other The other filter to be combined.
         * @return A combined filter that represents the logical AND of this filter and the other filter.
         */
        default AbstractFilter and(AbstractFilter other) {
            return path -> this.accept(path) && other.accept(path);
        }
    }
}
