package edu.hw8;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The Task2FixedThreadPool class implements the Task2ThreadPool interface,
 * providing a thread pool with a fixed number of threads.
 * This class manages a queue of tasks and executes them using its pool of worker threads.
 */
public class Task2FixedThreadPool implements Task2ThreadPool {
    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private volatile boolean isRunning;

    /**
     * Constructs a Task2FixedThreadPool with a specified number of threads.
     *
     * @param numberOfThreads The number of threads in the pool.
     */
    public Task2FixedThreadPool(int numberOfThreads) {
        threads = new Thread[numberOfThreads];
        taskQueue = new LinkedList<>();
        isRunning = true;
    }

    /**
     * Starts the thread pool by initializing and running the worker threads.
     * Each worker thread waits for tasks to be available in the queue and executes them.
     */
    @Override
    public void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                while (isRunning) {
                    Runnable task;
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty()) {
                            try {
                                taskQueue.wait();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                        task = taskQueue.poll();
                    }
                    try {
                        task.run();
                    } catch (RuntimeException ignored) {

                    }
                }
            });
            threads[i].start();
        }
    }

    /**
     * Adds a new task to the task queue and notifies a waiting thread to execute it.
     *
     * @param runnable The Runnable task to be executed.
     */
    @Override
    public void execute(Runnable runnable) {
        synchronized (taskQueue) {
            taskQueue.add(runnable);
            taskQueue.notify();
        }
    }

    /**
     * Stops the thread pool and interrupts all worker threads.
     * This method should be called to properly shut down the thread pool before exiting the application.
     */
    @Override
    public void close() {
        isRunning = false;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
