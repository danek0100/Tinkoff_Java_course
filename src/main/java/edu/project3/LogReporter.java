package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogReporter {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final int MAX_ROW_IN_TABLE = 5;
    private static final int SIZE = 1024;

    public static void processReport(Path output, List<String> filenames, LocalDate from, LocalDate to,
        LogAnalyzer statistics, OutputFormat format) {
        String reportText = generateReport(filenames, from, to, statistics, format);
        writeReport(output, reportText);
    }

    private static String generateReport(List<String> filenames, LocalDate from, LocalDate to,
        LogAnalyzer statistics, OutputFormat format) {
        String report = "";

        if (format == OutputFormat.MARKDOWN) {
            report = generateMarkdownReport(filenames, from, to, statistics);
        } else if (format == OutputFormat.ADOC) {
            report = generateAdocReport(filenames, from, to, statistics);
        }
        return report;
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static String generateMarkdownReport(List<String> filenames, LocalDate from, LocalDate to,
        LogAnalyzer statistics) {
        StringBuilder report = new StringBuilder();

        StringBuilder filenamesStr = new StringBuilder();
        for (String filename : filenames) {
            filenamesStr.append("`").append(filename).append("`,");
        }
        if (!filenamesStr.isEmpty()) {
            filenamesStr.deleteCharAt(filenamesStr.length() - 1);
        }

        report.append("#### Общая информация\n");
        report.append("<table>");
        appendKeyValue(report, "Файл(-ы)", filenamesStr.toString());
        appendKeyValue(report, "Начальная дата", from != null ? from.toString() : "-");
        appendKeyValue(report, "Конечная дата", to != null ? to.toString() : "-");
        appendKeyValue(report, "Количество запросов", String.format("%,d",
            statistics.statisticsInt.getOrDefault("totalRequests", 0L)));
        appendKeyValue(report, "Средний размер ответа",
            formatBytes(statistics.statisticsInt.getOrDefault("averageResponseSize", 0L)));
        report.append("</table>\n");

        report.append("\n#### Запрашиваемые ресурсы\n");
        appendResourceTable(report, statistics.statisticsResources.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n#### Коды ответа\n");
        appendResponseCodeTable(report, statistics.statisticsCodeAnswers.entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n#### Удалённые адреса\n");
        appendRemoteAddrTable(report, statistics.statisticsRemoteAddress.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n#### Агенты\n");
        appendAgentTable(report, statistics.statisticsAgents.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        return report.toString().replaceAll("\u00A0", "_");
    }

    private static void appendKeyValue(StringBuilder report, String key, String value) {
        report.append(String.format("<tr><td>%-20s</td><td>%-12s</td></tr>\n", key, value));
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static void appendTableHeader(StringBuilder report, String... headers) {
        report.append("<tr>");
        for (String header : headers) {
            report.append(String.format("<th>%-15s</th>", header));
        }
        report.append("</tr>\n");
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static void appendResourceTable(StringBuilder report, Map<String, Long> resourceStatistics) {
        report.append("<table>");
        appendTableHeader(report, "Ресурс", "Количество");

        resourceStatistics.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> appendTableRow(report, entry.getKey(), String.format("%,d", entry.getValue())));

        report.append("</table>\n");
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static void appendResponseCodeTable(StringBuilder report, Map<Integer, Long> responseCodeStatistics) {
        report.append("<table>");
        appendTableHeader(report, "Код", "Имя", "Количество");

        responseCodeStatistics.entrySet().stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .forEach(entry -> appendTableRow(report, entry.getKey().toString(),
                getResponseStatusName(entry.getKey()), String.format("%,d", entry.getValue())));

        report.append("</table>\n");
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static void appendRemoteAddrTable(StringBuilder report, Map<String, Long> remoteAddrStatistics) {
        report.append("<table>");
        appendTableHeader(report, "Адрес", "Количество");

        remoteAddrStatistics.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> appendTableRow(report, entry.getKey(), String.format("%,d", entry.getValue())));

        report.append("</table>\n");
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private static void appendAgentTable(StringBuilder report, Map<String, Long> agentStatistics) {
        report.append("<table>");
        appendTableHeader(report, "Имя", "Количество");

        agentStatistics.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> appendTableRow(report, entry.getKey(), String.format("%,d", entry.getValue())));

        report.append("</table>\n");
    }

    private static void appendTableRow(StringBuilder report, String... values) {
        report.append("<tr>");
        for (String value : values) {
            report.append(String.format("<td>%-15s</td>", value));
        }
        report.append("</tr>\n");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static String getResponseStatusName(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 304 -> "Not Modified";
            case 206 -> "Partial Content";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Unknown";
        };
    }

    private static String formatBytes(double bytes) {
        double newBytes = bytes;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        while (newBytes >= SIZE && unitIndex < units.length - 1) {
            newBytes /= SIZE;
            unitIndex++;
        }
        return String.format("%.2f %s", newBytes, units[unitIndex]);
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public static String generateAdocReport(List<String> filenames, LocalDate from, LocalDate to,
        LogAnalyzer statistics) {
        StringBuilder report = new StringBuilder();

        report.append("== Общая информация\n");
        appendKeyValueAdoc(report, "Файл(-ы)", filenames);
        appendKeyValueAdoc(report, "Начальная дата", from != null ? from.toString() : "-");
        appendKeyValueAdoc(report, "Конечная дата", to != null ? to.toString() : "-");
        appendKeyValueAdoc(report, "Количество запросов",
            String.format("%,d", statistics.statisticsInt.getOrDefault("totalRequests", 0L)));
        appendKeyValueAdoc(report, "Средний размер ответа",
            formatBytes(statistics.statisticsInt.getOrDefault("totalResponseSize", 0L)));
        report.append("|===\n");

        report.append("\n== Запрашиваемые ресурсы\n");
        appendTableHeaderAdoc(report, "Ресурс", "Количество");
        appendResourceTableAdoc(report, statistics.statisticsResources.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n== Коды ответа\n");
        appendTableHeaderAdoc(report, "Код", "Имя", "Количество");
        appendResponseCodeTableAdoc(report, statistics.statisticsCodeAnswers.entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n== Удалённые адреса\n");
        appendTableHeaderAdoc(report, "Адрес", "Количество");
        appendRemoteAddrTableAdoc(report, statistics.statisticsRemoteAddress.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        report.append("\n== Агенты\n");
        appendTableHeaderAdoc(report, "Имя", "Количество");
        appendAgentTableAdoc(report, statistics.statisticsAgents.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(MAX_ROW_IN_TABLE)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        return report.toString().replaceAll("\u00A0", "_");
    }

    private static void appendKeyValueAdoc(StringBuilder report, String key, Object value) {
        report.append(String.format("%s:: %s%n", key, value));
    }

    private static void appendTableHeaderAdoc(StringBuilder report, String... headers) {
        report.append("|===\n");
        for (String header : headers) {
            report.append(String.format("| %s%n", header));
        }
        report.append("|===\n");
    }

    private static void appendResourceTableAdoc(StringBuilder report, Map<String, Long> resourceStatistics) {
        for (Map.Entry<String, Long> entry : resourceStatistics.entrySet()) {
            appendTableRowAdoc(report, entry.getKey(), String.format("%,d", entry.getValue()));
        }
        report.append("|===\n");
    }

    private static void appendResponseCodeTableAdoc(StringBuilder report, Map<Integer, Long> responseCodeStatistics) {
        for (Map.Entry<Integer, Long> entry : responseCodeStatistics.entrySet()) {
            appendTableRowAdoc(report, entry.getKey().toString(), getResponseStatusName(entry.getKey()),
                String.format("%,d", entry.getValue()));
        }
        report.append("|===\n");
    }

    private static void appendTableRowAdoc(StringBuilder report, String... values) {
        report.append("|");
        for (String value : values) {
            report.append(String.format(" %s |", value));
        }
        report.append("\n");
    }

    private static void appendRemoteAddrTableAdoc(StringBuilder report, Map<String, Long> remoteAddrStatistics) {
        for (Map.Entry<String, Long> entry : remoteAddrStatistics.entrySet()) {
            appendTableRowAdoc(report, entry.getKey(), String.format("%,d", entry.getValue()));
        }
        report.append("|===\n");
    }

    private static void appendAgentTableAdoc(StringBuilder report, Map<String, Long> agentStatistics) {
        for (Map.Entry<String, Long> entry : agentStatistics.entrySet()) {
            appendTableRowAdoc(report, entry.getKey(), String.format("%,d", entry.getValue()));
        }
        report.append("|===\n");
    }


    private static void writeReport(Path file, String reportText) {
        try {
            Files.write(file, Collections.singletonList(reportText),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            LOGGER.info("Report written successfully to: " + file.toAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Error writing report to file: " + e.getMessage());
        }
    }

    private LogReporter() {}
}
