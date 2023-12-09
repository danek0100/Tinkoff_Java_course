package edu.hw9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task1StatsCollectorTest {

    private Task1StatsCollector collector;

    @BeforeEach
    public void setup() {
        collector = new Task1StatsCollector();
    }

    @Test
    public void testPushAndStats() {
        collector.push("metric1", new double[] {1.0, 2.0, 3.0});
        collector.push("metric2", new double[] {4.0, 5.0, 6.0});

        Map<String, Map<String, Double>> stats = collector.stats();

        assertThat(stats).containsKey("metric1");
        assertThat(stats.get("metric1")).containsOnlyKeys("sum", "average", "max", "min");
        assertThat(stats.get("metric1").get("sum")).isEqualTo(6.0);
        assertThat(stats.get("metric1").get("average")).isEqualTo(2.0);
        assertThat(stats.get("metric1").get("max")).isEqualTo(3.0);
        assertThat(stats.get("metric1").get("min")).isEqualTo(1.0);

        assertThat(stats).containsKey("metric2");
        assertThat(stats.get("metric2")).containsOnlyKeys("sum", "average", "max", "min");
        assertThat(stats.get("metric2").get("sum")).isEqualTo(15.0);
        assertThat(stats.get("metric2").get("average")).isEqualTo(5.0);
        assertThat(stats.get("metric2").get("max")).isEqualTo(6.0);
        assertThat(stats.get("metric2").get("min")).isEqualTo(4.0);
    }


    @Test
    public void testMultithreadedPushAndStats() throws InterruptedException {
        Task1StatsCollector collector = new Task1StatsCollector();
        int numberOfThreads = 6;
        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; i++) {
                int threadId = i;
                executorService.execute(() -> {
                    collector.push("metric" + threadId, new double[] {Math.random(), Math.random(), Math.random()});
                });
            }
        }
        // Проверка агрегированных данных
        Map<String, Map<String, Double>> stats = collector.stats();

        // Перебор метрик и проверка каждой
        for (Map.Entry<String, Map<String, Double>> entry : stats.entrySet()) {
            Map<String, Double> metricStats = entry.getValue();

            assertThat(metricStats).containsOnlyKeys("sum", "average", "max", "min");

            assertThat(metricStats.get("sum")).isGreaterThan(0.0);
            assertThat(metricStats.get("average")).isBetween(0.0, 1.0);
            assertThat(metricStats.get("max")).isBetween(0.0, 1.0);
            assertThat(metricStats.get("min")).isBetween(0.0, 1.0);
        }
    }
}
