package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @Test
    @DisplayName("Counter Should Be Thread-Safe")
    void testThreadSafetyOfCounter() throws InterruptedException {
        int numThreads = 50;
        int incrementTo = 1000;

        Task1 task = new Task1();
        task.incrementCounter(numThreads, incrementTo);

        assertThat(task.getCounter()).isEqualTo(numThreads * incrementTo);
    }

    @Test
    @DisplayName("One thread counter")
    void testOneThreadCounter() throws InterruptedException {
        int numThreads = 1;
        int incrementTo = 10;

        Task1 task = new Task1();
        task.incrementCounter(numThreads, incrementTo);

        assertThat(task.getCounter()).isEqualTo(10);
    }
}
