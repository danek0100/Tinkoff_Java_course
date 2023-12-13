package edu.hw10;

import edu.hw10.annotation.Cache;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * A cache proxy that uses method-level caching based on annotations.
 * This proxy can cache method results in memory and optionally persist them to disk.
 */
public class Task2CacheProxy {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Long> TIME_MAP = new ConcurrentHashMap<>();
    private static final Queue<String> ACCESS_QUEUE = new ConcurrentLinkedQueue<>();
    static final String CACHE_DIR = System.getProperty("java.io.tmpdir") + "/cacheDir";

    static {
        try {
            Files.createDirectories(Paths.get(CACHE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create cache directory", e);
        }
    }

    /**
     * Creates a proxy for the specified target object that implements the given interface.
     * The proxy caches method results based on annotations.
     *
     * @param target         The target object to proxy.
     * @param interfaceClass The interface to implement.
     * @param <T>            The type of the target and proxy objects.
     * @return A proxy that caches method results.
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(T target, Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            (proxy, method, args) -> {
                Cache cacheAnnotation = method.getAnnotation(Cache.class);
                if (cacheAnnotation != null) {
                    String cacheName = cacheAnnotation.cacheName().isEmpty() ? method.getName()
                        : cacheAnnotation.cacheName();
                    String key = cacheName + Arrays.toString(args);

                    synchronized (Task2CacheProxy.class) {
                        if (CACHE.containsKey(key)) {
                            updateAccessData(key);
                            return CACHE.get(key);
                        }

                        if (CACHE.size() >= cacheAnnotation.maxSize()) {
                            evictCache(cacheAnnotation.strategy());
                        }

                        if (cacheAnnotation.persist()) {
                            Object diskResult = loadFromDisk(key);
                            if (diskResult != null) {
                                CACHE.put(key, diskResult);
                                updateAccessData(key);
                                return diskResult;
                            }
                        }

                        Object result = method.invoke(target, args);
                        CACHE.put(key, result);
                        updateAccessData(key);

                        if (cacheAnnotation.persist()) {
                            saveToDisk(key, result);
                        }

                        return result;
                    }
                } else {
                    return method.invoke(target, args);
                }
            });
    }

    private static void updateAccessData(String key) {
        TIME_MAP.put(key, System.currentTimeMillis());
        ACCESS_QUEUE.remove(key);
        ACCESS_QUEUE.offer(key);
    }

    private static void evictCache(String strategy) {
        if ("LRU".equals(strategy)) {
            String lruKey = Collections.min(TIME_MAP.entrySet(), Map.Entry.comparingByValue()).getKey();
            removeCacheEntry(lruKey);
        } else if ("FIFO".equals(strategy)) {
            String fifoKey = ACCESS_QUEUE.poll();
            if (fifoKey != null) {
                removeCacheEntry(fifoKey);
            }
        }
    }

    private static void removeCacheEntry(String key) {
        CACHE.remove(key);
        TIME_MAP.remove(key);
        deleteFromDisk(key);
    }

    private static void saveToDisk(String key, Object value) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CACHE_DIR + "/" + key))) {
            oos.writeObject(value);
        } catch (IOException e) {
            LOGGER.error("Error saving to disk: " + e.getMessage());
        }
    }

    private static void deleteFromDisk(String key) {
        try {
            Files.deleteIfExists(Paths.get(CACHE_DIR + "/" + key));
        } catch (IOException e) {
            LOGGER.error("Error deleting from disk: " + e.getMessage());
        }
    }

    private static Object loadFromDisk(String key) {
        Path filePath = Paths.get(CACHE_DIR + "/" + key);
        if (Files.exists(filePath)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error("Error loading from disk: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Clears the in-memory cache and optionally clears the cache directory on disk.
     *
     * @param clearDiskCache If true, clears the cache directory on disk as well.
     * @throws IOException If there is an error while clearing the cache directory.
     */
    public static void clearCache(boolean clearDiskCache) throws IOException {
        CACHE.clear();
        TIME_MAP.clear();
        ACCESS_QUEUE.clear();

        if (clearDiskCache) {
            try (Stream<Path> paths = Files.walk(Paths.get(CACHE_DIR))) {
                paths.filter(Files::isRegularFile).forEach(filePath -> {
                    try {
                        Files.delete(filePath);
                    } catch (IOException e) {
                        LOGGER.error("Error deleting file: " + e.getMessage());
                    }
                });
            } catch (IOException e) {
                throw new IOException("Error accessing cache directory: " + e.getMessage());
            }
        }
    }

    /**
     * Gets the current size of the in-memory cache.
     *
     * @return The number of items in the cache.
     */
    static int getCacheSize() {
        return CACHE.size();
    }

    /**
     * Checks if an item with the specified key exists in the in-memory cache.
     *
     * @param key The key to check for in the cache.
     * @return true if the item exists in the cache, false otherwise.
     */
    static boolean containsInCache(String key) {
        return CACHE.containsKey(key);
    }

    private Task2CacheProxy() {}
}
