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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentsAnalyzer {
    private static final List<String> AVAILABLE_ARGS = Arrays.asList("--path", "--from", "--to", "--format");

    private enum ParseState {
        PATH,
        FROM,
        TO,
        FORMAT
    }

    private LocalDate from = null;
    private LocalDate to = null;
    private OutputFormat format = OutputFormat.MARKDOWN;
    private final List<LogSource> sourceList = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    private final static Logger LOGGER = LogManager.getLogger();


    ArgumentsAnalyzer() {

    }

    public void argumentsAnalyze(String[] args) {
        ParseState parseState = null;

        for (String arg : args) {

            if (AVAILABLE_ARGS.contains(arg)) {
                parseState = ParseState.valueOf(arg.substring(2).toUpperCase());
                continue;
            }

            if (parseState == null) {
                throw new IllegalArgumentException("Unexpected argument: " + arg);
            }

            switch (parseState) {
                case PATH -> {
                    LogSource logSource = detectPathType(arg);
                    if (logSource == null) {
                        throw new IllegalArgumentException("Path not valid!");
                    }
                    sourceList.add(logSource);
                }
                case FROM -> {
                    LocalDate parsedDate = LocalDate.parse(arg, formatter);
                    from = parsedDate;
                }
                case TO -> {
                    LocalDate parsedDate = LocalDate.parse(arg, formatter);
                    to = parsedDate;
                }
                case FORMAT -> {
                    OutputFormat parsedFormat;
                    if ("adoc".equals(arg)) {
                        parsedFormat = OutputFormat.ADOC;
                    } else {
                        parsedFormat = OutputFormat.MARKDOWN;
                    }
                    format = parsedFormat;
                }
                default -> throw new IllegalStateException("Unexpected parse state: " + arg);
            }
        }
    }

    private LogSource detectPathType(String pathString) {
        if (pathString.startsWith("http") || pathString.startsWith("https")) {
            try {
                URI uri = new URI(pathString);
                return new LogSource(pathString, LogSource.LogType.URI);
            } catch (URISyntaxException uriSyntaxException) {
                LOGGER.error(uriSyntaxException);
            }
        }
        try {
            Path localPath = Paths.get(pathString);
            return new LogSource(pathString, LogSource.LogType.PATH);
        } catch (InvalidPathException invalidPathException) {
            LOGGER.error(invalidPathException);
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
