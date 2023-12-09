package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class LogAnalyzer {

    private LocalDate from = null;
    private LocalDate to = null;

    public final Map<String, Long> statisticsInt = new HashMap<>();
    public final Map<Integer, Long> statisticsCodeAnswers = new HashMap<>();
    public final Map<String, Long> statisticsResources = new HashMap<>();
    public final Map<String, Long> statisticsRemoteAddress = new HashMap<>();
    public final Map<String, Long> statisticsAgents = new HashMap<>();
    public final Map<OffsetDateTime, Long> statisticsRequestsPerMinute = new HashMap<>();

    public static final String TOTAL_REQUESTS = "totalRequests";
    public static final String TOTAL_RESPONSE_SIZE = "totalResponseSize";
    public static final String AVERAGE_RESPONSE_SIZE = "averageResponseSize";
    public static final String MAX_REQUESTS_PER_MINUTE = "maxRequestsPerMinute";


    public void analyzeLog(LogSource log) throws IOException, URISyntaxException, InterruptedException {
        if (log.type() == LogSource.LogType.PATH) {
            analyzeLogsFromFile(Paths.get(log.path()));
        } else if (log.type() == LogSource.LogType.URI) {
            List<LogRecord> records = parseLogsFromURL(new URI(log.path()));
            records.forEach(this::updateStatistics);
        }
    }

    public void analyzeLogsFromFile(Path filePath) throws IOException {
        // Используем ленивое чтение из файла
        try (var lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            lines.forEach(line -> updateStatistics(LogAnalyzer.parseLogEntry(line)));
        }
    }

    public List<LogRecord> parseLogsFromURL(URI url) throws IOException, InterruptedException {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            List<LogRecord> logRecords = new ArrayList<>();
            try (var lines = response.body().lines()) {
                lines.forEach(line -> logRecords.add(parseLogEntry(line)));
            }
            return logRecords;
        }
    }


    private static LogRecord parseLogEntry(String logLine) {
        Matcher matcher = LogRecord.LOG_PATTERN.matcher(logLine);
        if (matcher.matches()) {
            return new LogRecord(matcher.group("remoteAddr"),
                                OffsetDateTime.parse(matcher.group("timeLocal"), LogRecord.LOG_DATE_FORMATTER),
                                matcher.group("request"),
                                Integer.parseInt(matcher.group("status")),
                                Long.parseLong(matcher.group("bodyBytesSent")),
                                matcher.group("referer"),
                                matcher.group("userAgent"));
        } else {
            throw new IllegalArgumentException("Invalid log entry: " + logLine);
        }
    }

    void updateStatistics(LogRecord logRecord) {
        LocalDate ldt = logRecord.timeLocal().toLocalDate();
        if ((from != null && ldt.isBefore(from)) || (to != null && ldt.isAfter(to))) {
          return;
        }

        updateCount(statisticsInt, TOTAL_REQUESTS);
        updateSum(statisticsInt, TOTAL_RESPONSE_SIZE, logRecord.bodyBytesSent());
        updateAverage(statisticsInt,  TOTAL_RESPONSE_SIZE, AVERAGE_RESPONSE_SIZE, TOTAL_REQUESTS);

        updateIntKeyCount(statisticsCodeAnswers, logRecord.status());
        updateCount(statisticsResources, logRecord.referer());
        updateCount(statisticsAgents, logRecord.userAgent());
        updateCount(statisticsRemoteAddress, logRecord.remoteAddr());

        OffsetDateTime timeRoundedToMinute = logRecord.timeLocal().withSecond(0).withNano(0);
        statisticsRequestsPerMinute.put(timeRoundedToMinute,
            statisticsRequestsPerMinute.getOrDefault(timeRoundedToMinute, 0L) + 1);

        updateMaxRequestsPerMinute(statisticsRequestsPerMinute, statisticsInt, MAX_REQUESTS_PER_MINUTE);
    }

    private static void updateCount(Map<String, Long> statistics, String key) {
        statistics.put(key, statistics.getOrDefault(key, 0L) + 1);
    }

    private static void updateIntKeyCount(Map<Integer, Long> statistics, Integer key) {
        statistics.put(key, statistics.getOrDefault(key, 0L) + 1);
    }


    private static void updateSum(Map<String, Long> statistics, String key, long value) {
        statistics.put(key, statistics.getOrDefault(key, 0L) + value);
    }

    private static void updateAverage(Map<String, Long> intStatistics, String sumKey,
        String averageKey, String countKey) {
        long total = intStatistics.getOrDefault(sumKey, 0L);
        long count = intStatistics.getOrDefault(countKey, 0L);

        intStatistics.put(sumKey, total + count);
        intStatistics.put(averageKey, total / count);
    }

    private static void updateMaxRequestsPerMinute(Map<OffsetDateTime, Long> statisticsRequestsPerMinute,
        Map<String, Long> intStatistics, String key) {
        long maxRequests = statisticsRequestsPerMinute.values().stream().max(Long::compare).orElse(0L);
        if (maxRequests > intStatistics.getOrDefault(key, 0L)) {
            intStatistics.put(key, maxRequests);
        }
    }

    public void clearStatistics() {
        statisticsAgents.clear();
        statisticsResources.clear();
        statisticsInt.clear();
        statisticsCodeAnswers.clear();
        statisticsRemoteAddress.clear();
        statisticsRequestsPerMinute.clear();
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
