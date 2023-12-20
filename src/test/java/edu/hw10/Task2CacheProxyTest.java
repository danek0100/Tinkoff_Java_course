package edu.hw10;

import edu.hw10.annotation.Cache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2CacheProxyTest {

    private FibCalculator proxy;
    private FibCalculator simpleFibCalculator;
    private MatrixCalculator calculator;

    @BeforeEach
    public void setup() {
        FibCalculator fibCalculator = new SelfProxyFibCalculatorImpl();
        proxy = Task2CacheProxy.create(fibCalculator, FibCalculator.class);
        ((SelfProxyFibCalculatorImpl) fibCalculator).setSelfProxy(proxy);

        simpleFibCalculator = Task2CacheProxy.create(new FibCalculatorImpl(), FibCalculator.class);
        calculator = Task2CacheProxy.create(new MatrixCalculatorImpl(), MatrixCalculator.class);

    }

    @AfterEach
    public void clear() throws IOException {
        Task2CacheProxy.clearCache(true);
    }

    @Test
    public void testCachingPerformance() throws IOException {
        long startTime = System.nanoTime();
        long resultWithoutCache = proxy.fib(35);
        long durationWithoutCache = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long resultWithCache = proxy.fib(35);
        long durationWithCache = System.nanoTime() - startTime;

        assertThat(resultWithCache).isEqualTo(resultWithoutCache);

        assertThat(durationWithCache).isLessThan(durationWithoutCache);

        Task2CacheProxy.clearCache(true);

        startTime = System.nanoTime();
        resultWithoutCache = simpleFibCalculator.fib(35);
        durationWithoutCache = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        resultWithCache = simpleFibCalculator.fib(35);
        durationWithCache = System.nanoTime() - startTime;

        assertThat(resultWithCache).isEqualTo(resultWithoutCache);

        assertThat(durationWithCache).isLessThan(durationWithoutCache);
    }

    @Test
    public void testPersistenceToDisk() throws IOException {
        proxy.fib(5);
        String cacheKey = "fib" + "[5]";
        String cacheFilePath = Task2CacheProxy.CACHE_DIR + "/" + cacheKey;

        Path cacheFile = Paths.get(cacheFilePath);
        assertThat(Files.exists(cacheFile)).isTrue();

        Task2CacheProxy.clearCache(false);
        assertThat(Files.exists(cacheFile)).isTrue();

        Task2CacheProxy.clearCache(true);
        assertThat(Files.exists(cacheFile)).isFalse();
    }

    @Test
    public void testCustomCacheName() {
        simpleFibCalculator.fibAnnotated(5);
        String customCacheKey = "testName" + "[5]";
        String cacheFilePath = Task2CacheProxy.CACHE_DIR + "/" + customCacheKey;

        assertThat(Files.exists(Paths.get(cacheFilePath))).isTrue();
    }

    @Test
    public void testMaxSize() {
        for (int i = 0; i < 5; i++) {
            simpleFibCalculator.fibAnnotated(i);
        }
        assertThat(Task2CacheProxy.getCacheSize()).isLessThanOrEqualTo(2);
    }


    @Test
    public void testEvictionStrategy() {
        //FIFO
        simpleFibCalculator.fibAnnotated(1);
        simpleFibCalculator.fibAnnotated(2);
        simpleFibCalculator.fibAnnotated(1);
        simpleFibCalculator.fibAnnotated(3);

        assertThat(Task2CacheProxy.containsInCache("testName[1]")).isTrue();
        assertThat(Task2CacheProxy.containsInCache("testName[2]")).isFalse();
        assertThat(Task2CacheProxy.containsInCache("testName[3]")).isTrue();
    }

    @Test
    public void testMatrixMultiplicationCachingPerformance() {
        double[][] matrix1 = createLargeMatrix(100, 100);
        double[][] matrix2 = createLargeMatrix(100, 100);

        long startTime = System.nanoTime();
        double[][] resultWithoutCache = calculator.matrixMultiply(matrix1, matrix2);
        long durationWithoutCache = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        double[][] resultWithCache = calculator.matrixMultiply(matrix1, matrix2);
        long durationWithCache = System.nanoTime() - startTime;

        assertThat(Task2CacheProxy.containsInCache("matrixMultiply[" + matrix1 + ", " + matrix2 + "]")).isTrue();
        assertThat(durationWithCache).isLessThan(durationWithoutCache);

        for (int i = 0; i < resultWithoutCache.length; ++i) {
            for (int j = 0; j < resultWithoutCache[0].length; ++j) {
                assertThat(resultWithoutCache[i][j]).isEqualTo(resultWithCache[i][j]);
            }
        }
    }

    // Вспомогательные классы для тестирования
    public interface FibCalculator {
        @Cache(persist = true)
        long fib(int number);

        @Cache(persist = true, maxSize = 2, strategy = "FIFO", cacheName = "testName")
        long fibAnnotated(int number);
    }

    public static class SelfProxyFibCalculatorImpl implements FibCalculator {
        private FibCalculator selfProxy;

        public void setSelfProxy(FibCalculator selfProxy) {
            this.selfProxy = selfProxy;
        }

        @Override
        public long fib(int number) {
            if (number <= 1) return 1;
            return selfProxy.fib(number - 1) + selfProxy.fib(number - 2);
        }

        @Override
        public long fibAnnotated(int number) {
            if (number <= 1) return 1;
            return selfProxy.fib(number - 1) + selfProxy.fib(number - 2);
        }
    }

    public static class FibCalculatorImpl implements FibCalculator {

        @Override
        public long fib(int number) {
            if (number <= 1) return 1;
            return fib(number - 1) + fib(number - 2);
        }

        @Override
        public long fibAnnotated(int number) {
            if (number <= 1) return 1;
            return fib(number - 1) + fib(number - 2);
        }
    }


    public interface MatrixCalculator {
        @Cache(persist = true, maxSize = 10, strategy = "LRU", cacheName = "matrixMultiply")
        double[][] matrixMultiply(double[][] matrix1, double[][] matrix2);
    }

    public static class MatrixCalculatorImpl implements MatrixCalculator {

        @Override
        public double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
            int m1Rows = matrix1.length;
            int m1Cols = matrix1[0].length;
            int m2Cols = matrix2[0].length;
            double[][] result = new double[m1Rows][m2Cols];

            for (int i = 0; i < m1Rows; i++) {
                for (int j = 0; j < m2Cols; j++) {
                    for (int k = 0; k < m1Cols; k++) {
                        result[i][j] += matrix1[i][k] * matrix2[k][j];
                    }
                }
            }
            return result;
        }
    }

    private double[][] createLargeMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }
}
