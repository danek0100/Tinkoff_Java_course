package edu.hw10;

import edu.hw10.annotation.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;


/**
 * A cache proxy that uses method-level caching based on annotations.
 * This proxy can cache method results in memory and optionally persist them to disk.
 */
public class Task2CacheProxy {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static final Map<String, Long> timeMap = new ConcurrentHashMap<>();
    private static final Queue<String> accessQueue = new ConcurrentLinkedQueue<>();
    static final String cacheDir = System.getProperty("java.io.tmpdir") + "/cacheDir";

    static {
        try {
            Files.createDirectories(Paths.get(cacheDir));
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
                    String cacheName = cacheAnnotation.cacheName().isEmpty() ? method.getName() : cacheAnnotation.cacheName();
                    String key = cacheName + Arrays.toString(args);

                    synchronized (Task2CacheProxy.class) {
                        if (cache.containsKey(key)) {
                            updateAccessData(key);
                            return cache.get(key);
                        }

                        if (cache.size() >= cacheAnnotation.maxSize()) {
                            evictCache(cacheAnnotation.strategy());
                        }

                        if (cacheAnnotation.persist()) {
                            Object diskResult = loadFromDisk(key);
                            if (diskResult != null) {
                                cache.put(key, diskResult);
                                updateAccessData(key);
                                return diskResult;
                            }
                        }

                        Object result = method.invoke(target, args);
                        cache.put(key, result);
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
        timeMap.put(key, System.currentTimeMillis());
        accessQueue.remove(key);
        accessQueue.offer(key);
    }

    private static void evictCache(String strategy) {
        if ("LRU".equals(strategy)) {
            String lruKey = Collections.min(timeMap.entrySet(), Map.Entry.comparingByValue()).getKey();
            removeCacheEntry(lruKey);
        } else if ("FIFO".equals(strategy)) {
            String fifoKey = accessQueue.poll();
            if (fifoKey != null) {
                removeCacheEntry(fifoKey);
            }
        }
    }

    private static void removeCacheEntry(String key) {
        cache.remove(key);
        timeMap.remove(key);
        deleteFromDisk(key);
    }

    private static void saveToDisk(String key, Object value) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cacheDir + "/" + key))) {
            oos.writeObject(value);
        } catch (IOException e) {
            LOGGER.error("Error saving to disk: " + e.getMessage());
        }
    }

    private static void deleteFromDisk(String key) {
        try {
            Files.deleteIfExists(Paths.get(cacheDir + "/" + key));
        } catch (IOException e) {
            LOGGER.error("Error deleting from disk: " + e.getMessage());
        }
    }

    private static Object loadFromDisk(String key) {
        Path filePath = Paths.get(cacheDir + "/" + key);
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
        cache.clear();
        timeMap.clear();
        accessQueue.clear();

        if (clearDiskCache) {
            try (Stream<Path> paths = Files.walk(Paths.get(cacheDir))) {
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
        return cache.size();
    }

    /**
     * Checks if an item with the specified key exists in the in-memory cache.
     *
     * @param key The key to check for in the cache.
     * @return true if the item exists in the cache, false otherwise.
     */
    static boolean containsInCache(String key) {
        return cache.containsKey(key);
    }
}
