package edu.project3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NginxLogStats {

    private final ArgumentsAnalyzer argumentsAnalyzer = new ArgumentsAnalyzer();

    public void getStatics(String[] args) {
        argumentsAnalyzer.argumentsAnalyze(args);
        if (argumentsAnalyzer.getSourceList().isEmpty()) {
            return;
        }

        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.setFrom(argumentsAnalyzer.getFrom());
        logAnalyzer.setTo(argumentsAnalyzer.getTo());
        argumentsAnalyzer.getSourceList().forEach(log -> {
            try {
                logAnalyzer.analyzeLog(log);
            } catch (IOException | URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        LogReporter.processReport(
            Path.of(getReportName()), argumentsAnalyzer.getSourceList().stream().map(LogSource::path).toList(),
            argumentsAnalyzer.getFrom(), argumentsAnalyzer.getTo(), logAnalyzer, argumentsAnalyzer.getFormat());
    }

    private String getReportName() {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return  "nginix_report_" + formattedDate
            + (argumentsAnalyzer.getFormat() == OutputFormat.MARKDOWN ? ".md" : ".adoc");
    }
}
