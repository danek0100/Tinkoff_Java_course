package edu.project3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NginxLogStatsTest {

    private Path tempLogFile1;
    private Path tempLogFile2;

    @BeforeEach
    void setUp() throws IOException {
        tempLogFile1 = Files.createTempFile("log1", ".txt");
        tempLogFile2 = Files.createTempFile("log2", ".txt");

        Files.write(tempLogFile1, "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"".getBytes(), StandardOpenOption.WRITE);
        Files.write(tempLogFile2, "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"".getBytes(), StandardOpenOption.WRITE);
    }

    @Test
    void testGetStatics() throws IOException, InterruptedException {
        NginxLogStats nginxLogStats = new NginxLogStats();

        nginxLogStats.getStatics(new String[]{"--path", tempLogFile1.toString(), tempLogFile2.toString(), "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs", "--to",  "2015-07-15", "--from", "2015-06-01"});

        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String expectedReportName = "nginix_report_" + formattedDate + ".md";
        Path expectedReportPath = Path.of(expectedReportName);
        assertTrue(Files.exists(expectedReportPath));

        nginxLogStats.getStatics(new String[]{"--path", tempLogFile1.toString(), "--to",  "2015-07-15", "--from", "2015-06-01", "--format", "adoc"});
        formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        expectedReportName = "nginix_report_" + formattedDate + ".adoc";
        expectedReportPath = Path.of(expectedReportName);
        assertTrue(Files.exists(expectedReportPath));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempLogFile1);
        Files.deleteIfExists(tempLogFile2);
    }
}
