package edu.hw8;

import java.util.LinkedList;
import java.util.Queue;

public class Task2FixedThreadPool implements Task2ThreadPool {
    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private volatile boolean isRunning;

    public Task2FixedThreadPool(int numberOfThreads) {
        threads = new Thread[numberOfThreads];
        taskQueue = new LinkedList<>();
        isRunning = true;
    }

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

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskQueue) {
            taskQueue.add(runnable);
            taskQueue.notify();
        }
    }

    @Override
    public void close() {
        isRunning = false;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
