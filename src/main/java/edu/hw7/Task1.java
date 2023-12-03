package edu.hw7;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * The {@code Task1} class provides a thread-safe way to increment a shared counter.
 * It uses {@code AtomicInteger} to ensure that all increments are atomic operations,
 * thereby avoiding race conditions in a multi-threaded environment.
 */
public class Task1 {
    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Increments the counter by a specified amount in a multi-threaded manner.
     *
     * @param numThreads   the number of threads to be used for incrementing.
     * @param incrementTo  the number of times each thread should increment the counter.
     * @throws InterruptedException if any thread is interrupted while waiting.
     */
    public void incrementCounter(int numThreads, int incrementTo) throws InterruptedException {
        List<Thread> threads = Stream.generate(() -> new Thread(() -> {
                for (int j = 0; j < incrementTo; j++) {
                    counter.incrementAndGet();
                } }))
            .limit(numThreads)
            .toList();

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * Retrieves the current value of the counter.
     *
     * @return the current value of the counter.
     */
    public int getCounter() {
        return counter.get();
    }
}
