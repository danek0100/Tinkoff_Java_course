package edu.project3;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgumentsAnalyzer {
    private static final List<String> AVAILABLE_ARGS = Arrays.asList("--path", "--from", "--to", "--format");
    private LocalDate from = null;
    private LocalDate to = null;
    private OutputFormat format = OutputFormat.MARKDOWN;
    private final List<LogSource> sourceList = new ArrayList<>();
    private static final int THIRD_STATE = 3;

    ArgumentsAnalyzer() {

    }

    public void argumentsAnalyze(String[] args) {
        int parseState = -1;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        for (String arg : args) {
            if (AVAILABLE_ARGS.contains(arg)) {
                parseState = AVAILABLE_ARGS.indexOf(arg);
            } else if (parseState == 0) {
                LogSource logSource = detectPathType(arg);
                if (logSource == null) {
                    throw new IllegalArgumentException("Path not valid!");
                }
                sourceList.add(logSource);
            } else if (parseState == 1) {
                from = LocalDate.parse(arg, formatter);
            } else if (parseState == 2) {
                to = LocalDate.parse(arg, formatter);
            } else if (parseState == THIRD_STATE) {
                format = arg.equals("adoc") ? OutputFormat.ADOC : OutputFormat.MARKDOWN;
            }
        }
    }

    private LogSource detectPathType(String pathString) {
        if (pathString.startsWith("http") || pathString.startsWith("https")) {
            try {
                URI uri = new URI(pathString);
                return new LogSource(pathString, LogSource.LogType.URI);
            } catch (URISyntaxException ignored) {

            }
        }
        try {
            Path localPath = Paths.get(pathString);
            return new LogSource(pathString, LogSource.LogType.PATH);
        } catch (InvalidPathException ignored) {
        }
        return null;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public OutputFormat getFormat() {
        return format;
    }

    public List<LogSource> getSourceList() {
        return sourceList;
    }
}
