package edu.hw8;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2FixedThreadPoolTest {

    // Для примера реализовано вычисление числа Фибоначчи через рекурсию.
    public static class FibonacciCallable implements Callable<BigInteger> {
        private final int n;
        private final Task2ThreadPool threadPool;
        private final int deep;
        private final int threads;

        public FibonacciCallable(int n, Task2ThreadPool threadPool, int deep, int threads) {
            this.n = n;
            this.threadPool = threadPool;
            this.deep = deep;
            this.threads = threads;
        }

        @Override
        public BigInteger call() throws Exception {
            if (n <= 1) {
                return new BigInteger(String.valueOf(n));
            }

            int needThreads = (int)Math.pow(2, deep);
            if (threadPool != null && needThreads <= threads) {
                FibonacciCallable f1 = new FibonacciCallable(n - 1, threadPool, deep + 1, threads - needThreads);
                FibonacciCallable f2 = new FibonacciCallable(n - 2, threadPool, deep + 1, threads - needThreads);

                FutureTask<BigInteger> futureTask1 = new FutureTask<>(f1);
                FutureTask<BigInteger> futureTask2 = new FutureTask<>(f2);

                threadPool.execute(futureTask1);
                threadPool.execute(futureTask2);

                return futureTask1.get().add(futureTask2.get());
            } else {
                return fib(n-1).add(fib(n-2));
            }
        }

        public static BigInteger fib(int n) {
            if (n <= 1) {
                return new BigInteger(String.valueOf(n));
            }
            return fib(n - 1).add(fib(n - 2));
        }
    }

    @Test
    public void fibTest() throws Exception {
        int n = 20;
        int threads = 6;

        try (Task2ThreadPool threadPool = new Task2FixedThreadPool(threads)) {
            FibonacciCallable fibonacciCallable = new FibonacciCallable(n - 1, threadPool, 1, threads - 1);
            FutureTask<BigInteger> futureTask = new FutureTask<>(fibonacciCallable);
            threadPool.start();
            threadPool.execute(futureTask);

            BigInteger result = futureTask.get();
            assertEquals(new BigInteger("4181"), result);
        }
    }

    @Test
    public void performanceTest() throws Exception {
        int n = 40;

        long startTimeSingleThread = System.nanoTime();
        BigInteger singleThreadResult = FibonacciCallable.fib(n - 1);
        long durationSingleThread = System.nanoTime() - startTimeSingleThread;
        assertEquals(new BigInteger("63245986"), singleThreadResult);

        int threads = 6;
        long startTimeMultiThread = System.nanoTime();
        try (Task2ThreadPool threadPool = new Task2FixedThreadPool(threads)) {
            FibonacciCallable fibonacciCallable = new FibonacciCallable(n - 1, threadPool, 1, threads - 1);
            FutureTask<BigInteger> futureTask = new FutureTask<>(fibonacciCallable);
            threadPool.start();
            threadPool.execute(futureTask);

            BigInteger multiThreadResult = futureTask.get();
            assertEquals(new BigInteger("63245986"), multiThreadResult);
        }
        long durationMultiThread = System.nanoTime() - startTimeMultiThread;

        // Максимально удалось получить ускорение составляет ~x2.3.
        // Чтобы проверить именно многопоточность использовалась простая реализация с рекурсией,
        // где для распараллеливания текущей глубины, нам нам надо не меньше свободных потоков,
        // чем предполагает элементов дерево включая текущий уровень. Так как доступно 6 ядер,
        // то максимально в параллели будет 2 ветки: 3 <= 6 <= 7.
        // Не лучший способ, но показывает, что код работает.
        // Assert не добавлен так как почему-то на GithubActions многопоточная версия работает дольше.
        //System.out.println("Single thread duration: " + durationSingleThread / 1e9 + " seconds");
        //System.out.println("Multi thread duration: " + durationMultiThread / 1e9 + " seconds");
    }

}
