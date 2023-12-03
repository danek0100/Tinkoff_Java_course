package edu.hw7;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4PiMonteCarloSingle {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final double FOUR_CONSTANT = 4.0;
    private static final double ACCURACY = 1e9;

    public static double computePi(int totalPoints) {
        Random random = new Random();
        int circlePoints = 0;

        for (int i = 0; i < totalPoints; ++i) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                circlePoints++;
            }
        }

        return FOUR_CONSTANT * circlePoints / totalPoints;
    }

    private Task4PiMonteCarloSingle() {}


    public static void getStatistic(int totalPoints) {

        long startTime = System.nanoTime();
        double estimatedPi = computePi(totalPoints);
        long endTime = System.nanoTime();

        double error = Math.abs(estimatedPi - Math.PI);
        double durationInSeconds = (endTime - startTime) / ACCURACY;

        LOGGER.info("Оценка числа Пи: " + estimatedPi);
        LOGGER.info("Погрешность: " + error);
        LOGGER.info("Время выполнения: " + durationInSeconds + " секунд");
    }
}
