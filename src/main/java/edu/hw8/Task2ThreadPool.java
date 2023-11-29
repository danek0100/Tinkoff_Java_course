package edu.hw8;

public interface Task2ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}
