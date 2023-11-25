package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4PiMonteCarloMulti {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final double FOUR_CONSTANT = 4.0;
    private static final double ACCURACY = 1e9;

    public static double computePi(int totalPoints, int numThreads) throws Exception {
        int totalCirclePoints = 0;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
            List<Future<Integer>> futures = new ArrayList<>();

            final int pointsPerThread = totalPoints / numThreads;
            final int remainder = totalPoints % numThreads;

            for (int i = 0; i < numThreads; ++i) {
                int pointsForThisThread = pointsPerThread + (i < remainder ? 1 : 0);
                futures.add(executor.submit(() -> {
                    int circlePoints = 0;
                    for (int j = 0; j < pointsForThisThread; ++j) {
                        double x = ThreadLocalRandom.current().nextDouble();
                        double y = ThreadLocalRandom.current().nextDouble();

                        if (x * x + y * y <= 1) {
                            ++circlePoints;
                        }
                    }
                    return circlePoints;
                }));
            }

            for (Future<Integer> future : futures) {
                totalCirclePoints += future.get();
            }
        }
        return FOUR_CONSTANT * totalCirclePoints / totalPoints;
    }

    public static void getStatistic(int totalPoints, int numThreads) throws Exception {

        long startTime = System.nanoTime();
        double estimatedPi = computePi(totalPoints, numThreads);
        long endTime = System.nanoTime();

        double error = Math.abs(estimatedPi - Math.PI);
        double durationInSeconds = (endTime - startTime) / ACCURACY;

        LOGGER.info("Оценка числа Пи: " + estimatedPi);
        LOGGER.info("Погрешность: " + error);
        LOGGER.info("Время выполнения: " + durationInSeconds + " секунд");
    }

    private Task4PiMonteCarloMulti() {}
}
