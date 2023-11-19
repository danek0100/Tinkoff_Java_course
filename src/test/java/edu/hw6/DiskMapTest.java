package edu.hw6;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DiskMapTest {
    private Path testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        testFilePath = Files.createTempFile("test", ".txt");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    @DisplayName("Test put and get functionality")
    public void testPutAndGet() throws IOException {
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            {
                map.put("key1", "value1");
                assertThat(map.get("key1")).isEqualTo("value1");

                map.put("key1", "value2");
                assertThat(map.get("key1")).isEqualTo("value2");

                assertThat(map.get("nonExistentKey")).isNull();
            }
        }
    }

    @Test
    @DisplayName("Test remove functionality")
    public void testRemove() throws IOException {
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            map.put("key1", "value1");
            assertThat(map.remove("key1")).isEqualTo("value1");
            assertThat(map.get("key1")).isNull();
        }
    }

    @Test
    @DisplayName("Test putAll functionality")
    public void testPutAll() throws IOException {
        Map<String, String> newEntries = new HashMap<>();
        newEntries.put("key1", "value1");
        newEntries.put("key2", "value2");

        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            map.putAll(newEntries);
            assertThat(map.get("key1")).isEqualTo("value1");
            assertThat(map.get("key2")).isEqualTo("value2");
        }
    }

    @Test
    @DisplayName("Test clear functionality")
    public void testClear() throws IOException {
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            map.put("key1", "value1");
            map.put("key2", "value2");

            map.clear();
            assertThat(map.isEmpty()).isTrue();
        }
    }

    @Test
    @DisplayName("Test IllegalArgumentException for invalid characters")
    public void testInvalidCharacters() throws IOException {
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            assertThrows(IllegalArgumentException.class, () -> map.put("key:1", "value1"));
            assertThrows(IllegalArgumentException.class, () -> map.put("key1", "value:1"));
        }
    }

    @Test
    @DisplayName("Write data to file and read it back")
    public void testWriteAndReadData() throws IOException {
        // Write data using DiskMap
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            map.put("testKey", "testValue");
        }

        // Read data using a new instance of DiskMap
        try (DiskMap map = new DiskMap(testFilePath.toString())) {
            assertThat(map.get("testKey")).isEqualTo("testValue");
        }
    }

    @Test
    @DisplayName("Construct DiskMap with an existing DiskMap using the same file")
    public void testConstructWithExistingFile() throws IOException {
        try (DiskMap map1 = new DiskMap(testFilePath.toString())) {
            assertThrows(IllegalStateException.class, () -> {
                try (DiskMap map2 = new DiskMap(testFilePath.toString())) {}
            });
        }
    }

}
