package edu.hw8;

/**
 * The Task2ThreadPool interface defines the contract for a custom thread pool.
 * It provides methods for starting the thread pool, executing tasks, and closing the pool.
 */
public interface Task2ThreadPool extends AutoCloseable {

    /**
     * Starts the thread pool and initiates its worker threads.
     */
    void start();

    /**
     * Submits a new task to the thread pool for execution.
     *
     * @param runnable The Runnable task to be executed.
     */
    void execute(Runnable runnable);
}
