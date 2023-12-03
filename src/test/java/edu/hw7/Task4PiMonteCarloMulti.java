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

}
