package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@code DiskMap} class implements a {@code Map<String, String>} interface and provides
 * functionality to store and retrieve key-value pairs with the data persisted on disk.
 * This implementation ensures that the same file is not used by multiple instances
 * concurrently by using a static set of open file paths.
 *
 * <p>Keys and values must not contain the ':' character, as it is used as a delimiter
 * in the storage file. An {@code IllegalArgumentException} will be thrown if a key or value
 * containing ':' is used. This class also implements {@code AutoCloseable} to release
 * file resources when no longer needed.</p>
 *
 */
public class DiskMap implements Map<String, String>, AutoCloseable {
    private static final Set<Path> OPEN_FILES = Collections.synchronizedSet(new HashSet<>());

    private Map<String, String> map = new HashMap<>();
    private final Path filePath;
    private static final String EXCEPTION_WHEN_SAVING_MESSAGE = "Failed to save changes to file";
    private static final String EXCEPTION_RESTRICTED_SYMBOLS = "Strings can not contain ':'";


    /**
     * Constructs a new {@code DiskMap} instance associated with the specified file.
     * If the file is already in use by another {@code DiskMap} instance, an
     * {@code IllegalStateException} is thrown.
     *
     * @param filename the name of the file used for storing key-value pairs.
     * @throws IOException if an I/O error occurs during file loading.
     * @throws IllegalArgumentException if the specified file is already in use.
     */
    public DiskMap(String filename) throws IOException {

        this.filePath = Paths.get(filename);

        synchronized (OPEN_FILES) {
            if (OPEN_FILES.contains(filePath)) {
                throw new IllegalStateException("File is already in use: " + filePath);
            }
            OPEN_FILES.add(filePath);
        }

        loadFromFile();
    }

    private void loadFromFile() throws IOException {
        if (!Files.exists(filePath)) {
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "r");
             FileChannel fileChannel = raf.getChannel();
             BufferedReader reader = new BufferedReader(Channels.newReader(fileChannel, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        }
    }

    private void saveToFile() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw");
             FileChannel fileChannel = raf.getChannel();
             BufferedWriter writer = new BufferedWriter(Channels.newWriter(fileChannel, StandardCharsets.UTF_8))) {

            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * <p>Note: Keys and values containing ':' are not allowed and will result in
     * {@code IllegalArgumentException}.</p>
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return the previous value associated with the key, or {@code null} if there was no mapping.
     * @throws IllegalArgumentException if the key or value contains ':'.
     * @throws IllegalStateException if an I/O error occurs during saving to file.
     */
    @Override
    public String put(String key, String value) {

        if (key.contains(":") || value.contains(":")) {
            throw new IllegalArgumentException(EXCEPTION_RESTRICTED_SYMBOLS);
        }

        String result = map.put(key, value);
        try {
            saveToFile();
        } catch (IOException e) {
            map.remove(key);
            throw new IllegalStateException(EXCEPTION_WHEN_SAVING_MESSAGE, e);
        }
        return result;
    }

    /**
     * Removes the mapping for a key from this map if it is present. The map will be
     * persisted to the disk after the removal.
     *
     * <p>If an I/O error occurs during saving to the file, the removal is rolled back
     * and an {@code IllegalStateException} is thrown.</p>
     *
     * @param key key whose mapping is to be removed from the map.
     * @return the previous value associated with the key, or {@code null} if there was no mapping.
     * @throws IllegalStateException if an I/O error occurs during saving to file.
     */
    @Override
    public String remove(Object key) {
        String value = map.remove(key);
        if (value != null) {
            try {
                saveToFile();
            } catch (IOException e) {
                map.put((String) key, value);
                throw new IllegalStateException(EXCEPTION_WHEN_SAVING_MESSAGE, e);
            }
        }
        return value;
    }

    /**
     * Copies all the mappings from the specified map to this map. The changes are
     * then persisted to the disk. Keys and values containing ':' are not allowed and
     * will result in {@code IllegalArgumentException}.
     *
     * <p>If an I/O error occurs during saving to the file, the operation is rolled back
     * and an {@code IllegalStateException} is thrown.</p>
     *
     * @param m mappings to be stored in this map.
     * @throws IllegalArgumentException if any key or value in the map contains ':'.
     * @throws IllegalStateException if an I/O error occurs during saving to file.
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {

        if (m.keySet().stream().anyMatch(key -> key.contains(":"))
            || m.values().stream().anyMatch(val -> val.contains(":"))) {
            throw new IllegalArgumentException(EXCEPTION_RESTRICTED_SYMBOLS);
        }

        map.putAll(m);
        try {
            saveToFile();
        } catch (IOException e) {
            m.forEach((key, value) -> map.remove(key, value));
            throw new IllegalStateException(EXCEPTION_WHEN_SAVING_MESSAGE, e);
        }
    }

    /**
     * Removes all the mappings from this map. The map will be empty after this
     * call returns and the changes will be persisted to the disk.
     *
     * <p>If an I/O error occurs during saving to the file, the clear operation is
     * rolled back and a {@code RuntimeException} is thrown.</p>
     *
     * @throws RuntimeException if an I/O error occurs during saving to file.
     */
    @Override
    public void clear() {
        Map<String, String> backup = new HashMap<>(map);
        map.clear();
        try {
            saveToFile();
        } catch (IOException e) {
            map = backup;
            throw new IllegalStateException(EXCEPTION_WHEN_SAVING_MESSAGE, e);
        }
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<String> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return map.get(key);
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     */
    @Override
    public void close() {
        synchronized (OPEN_FILES) {
            OPEN_FILES.remove(filePath);
        }
    }
}
