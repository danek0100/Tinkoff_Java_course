package edu.hw7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task4PiMonteCarloMultiTest {

    @Test
    void testPiValueAccuracyWithSingleThread() throws Exception {
        int totalPoints = 1000000;
        double piEstimate = Task4PiMonteCarloMulti.computePi(totalPoints, 1);
        double errorMargin = 0.01;

        assertTrue(Math.abs(piEstimate - Math.PI) < errorMargin, "Оценка числа Пи должна быть в пределах заданной погрешности при использовании одного потока");
    }

    @Test
    void testPiValueAccuracyWithMultipleThreads() throws Exception {
        int totalPoints = 1000000;
        int numThreads = Runtime.getRuntime().availableProcessors();
        double piEstimate = Task4PiMonteCarloMulti.computePi(totalPoints, numThreads);
        double errorMargin = 0.01;

        assertTrue(Math.abs(piEstimate - Math.PI) < errorMargin, "Оценка числа Пи должна быть в пределах заданной погрешности при использовании нескольких потоков");
    }

    @Test
    void testMultiThreadedPerformanceVsSingleThreaded() throws Exception {
        int totalPoints = 10_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors();
        if (numThreads > 1) {
            double durationSingle = 0.0;
            for (int i = 0; i < 10; ++i) {
                long startTimeSingle = System.nanoTime();
                Task4PiMonteCarloMulti.computePi(totalPoints, 1);
                long endTimeSingle = System.nanoTime();
                durationSingle += (endTimeSingle - startTimeSingle) / 1e9;
            }
            durationSingle /= 10;

            double durationMulti = 0.0;
            for (int i = 0; i < 10; ++i) {
                long startTimeMulti = System.nanoTime();
                Task4PiMonteCarloMulti.computePi(totalPoints, numThreads);
                long endTimeMulti = System.nanoTime();
                durationMulti += (endTimeMulti - startTimeMulti) / 1e9;
            }
            durationMulti /= 10;

            assertTrue(durationMulti < durationSingle, "Многопоточная версия должна работать быстрее однопоточной");
        }
    }
}
