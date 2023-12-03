package edu.hw7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task4PiMonteCarloSingleTest {

    @Test
    void testPiValueAccuracy() {
        int totalPoints = 1000000;
        double piEstimate = Task4PiMonteCarloSingle.computePi(totalPoints);
        double errorMargin = 0.01;

        assertTrue(Math.abs(piEstimate - Math.PI) < errorMargin, "Оценка числа Пи должна быть в пределах заданной погрешности");
    }

    @Test
    void testPerformance() {
        int totalPoints = 100000;
        long startTime = System.nanoTime();

        Task4PiMonteCarloSingle.getStatistic(totalPoints);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1e9;

        double maxDuration = 5.0;
        assertTrue(durationInSeconds < maxDuration, "Метод должен выполняться менее чем за " + maxDuration + " секунд");
    }
}
