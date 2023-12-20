package edu.hw9;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The Task1StatsCollector class is designed for collecting and aggregating statistical data from multiple threads.
 * It uses a ConcurrentHashMap to store numerical data associated with different metric names. Each metric's data
 * is collected in a ConcurrentLinkedQueue, allowing for thread-safe operations.
 */
public class Task1StatsCollector {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Double>> metrics = new ConcurrentHashMap<>();

    /**
     * Pushes an array of numerical values to the specified metric. If the metric does not exist, it is created.
     * Each value in the array is added to the ConcurrentLinkedQueue associated with the metric.
     *
     * @param metricName The name of the metric to which the values are to be added.
     * @param values An array of double values to be added to the metric.
     */
    public void push(String metricName, double[] values) {
        ConcurrentLinkedQueue<Double> queue = metrics.computeIfAbsent(metricName, k -> new ConcurrentLinkedQueue<>());
        for (double value : values) {
            queue.add(value);
        }
    }

    /**
     * Aggregates and returns statistical data for all metrics. This method calculates the sum, average, maximum,
     * and minimum values for each metric. The method uses parallel streams for efficient computation.
     *
     * @return A Map where each key is a metric name and the corresponding value is another Map. The inner Map
     *         contains statistical data (sum, average, max, min) for the metric.
     */
    public Map<String, Map<String, Double>> stats() {
        Map<String, Map<String, Double>> stats = new ConcurrentHashMap<>();
        metrics.keySet().parallelStream().forEach(metricName -> {
            List<Double> values = new ArrayList<>(metrics.get(metricName));
            DoubleSummaryStatistics summary =
                values.parallelStream().mapToDouble(Double::doubleValue).summaryStatistics();

            Map<String, Double> stat = new ConcurrentHashMap<>();
            stat.put("sum", summary.getSum());
            stat.put("average", summary.getAverage());
            stat.put("max", summary.getMax());
            stat.put("min", summary.getMin());
            stats.put(metricName, stat);
        });
        return stats;
    }
}
