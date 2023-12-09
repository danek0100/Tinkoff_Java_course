package edu.project3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogAnalyzerTest {

    private Path tempLogFile1;

    @BeforeEach
    void setUp() throws IOException {
        tempLogFile1 = Files.createTempFile("log1", ".txt");

        Files.write(tempLogFile1, "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"".getBytes(), StandardOpenOption.WRITE);
    }

    @Test
    void testParseLogsFromFile() throws IOException, URISyntaxException, InterruptedException {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        LogSource logSource = new LogSource(tempLogFile1.toString(), LogSource.LogType.PATH);

        logAnalyzer.analyzeLog(logSource);

        assertEquals(1, logAnalyzer.statisticsInt.get(LogAnalyzer.TOTAL_REQUESTS));
        assertEquals(1, logAnalyzer.statisticsInt.get(LogAnalyzer.TOTAL_RESPONSE_SIZE));
        assertEquals(1, logAnalyzer.statisticsCodeAnswers.get(304));
        assertEquals(1, logAnalyzer.statisticsRemoteAddress.get("93.180.71.3"));
        assertEquals(1, logAnalyzer.statisticsInt.get(LogAnalyzer.MAX_REQUESTS_PER_MINUTE));
    }

    @Test
    void testUpdateStatistics() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        LogRecord logRecord = new LogRecord("127.0.0.1", OffsetDateTime.now(), "GET /test", 200, 100L, null, "UserAgent");

        logAnalyzer.updateStatistics(logRecord);

        assertEquals(1, logAnalyzer.statisticsInt.get(LogAnalyzer.TOTAL_REQUESTS));
        assertEquals(1, logAnalyzer.statisticsCodeAnswers.get(200));
        assertEquals(1, logAnalyzer.statisticsAgents.get("UserAgent"));
        assertEquals(1, logAnalyzer.statisticsRemoteAddress.get("127.0.0.1"));
    }

    @Test
    void testClearStatistics() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.statisticsInt.put("test", 10L);
        logAnalyzer.statisticsCodeAnswers.put(200, 5L);
        logAnalyzer.statisticsResources.put("/test", 3L);
        logAnalyzer.statisticsAgents.put("UserAgent", 7L);
        logAnalyzer.statisticsRemoteAddress.put("127.0.0.1", 2L);

        logAnalyzer.clearStatistics();

        assertEquals(0, logAnalyzer.statisticsInt.size());
        assertEquals(0, logAnalyzer.statisticsCodeAnswers.size());
        assertEquals(0, logAnalyzer.statisticsResources.size());
        assertEquals(0, logAnalyzer.statisticsAgents.size());
        assertEquals(0, logAnalyzer.statisticsRemoteAddress.size());
        assertEquals(0, logAnalyzer.statisticsRequestsPerMinute.size());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempLogFile1);
    }
}
