package edu.project3;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsAnalyzerTest {

    @Test
    void testValidArgumentsAnalyze() {
        ArgumentsAnalyzer argumentsAnalyzer = new ArgumentsAnalyzer();
        String[] args = {"--path", "log.txt", "--from", "2023-01-01", "--to", "2023-12-31", "--format", "adoc"};

        argumentsAnalyzer.argumentsAnalyze(args);

        assertEquals(LocalDate.parse("2023-01-01"), argumentsAnalyzer.getFrom());
        assertEquals(LocalDate.parse("2023-12-31"), argumentsAnalyzer.getTo());
        assertEquals(OutputFormat.ADOC, argumentsAnalyzer.getFormat());

        List<LogSource> sourceList = argumentsAnalyzer.getSourceList();
        assertEquals(1, sourceList.size());
        assertEquals("log.txt", sourceList.get(0).path());
        assertEquals(LogSource.LogType.PATH, sourceList.get(0).type());
    }

    @Test
    void testInvalidDateFormatArgumentsAnalyze() {
        ArgumentsAnalyzer argumentsAnalyzer = new ArgumentsAnalyzer();
        String[] args = {"--path", "log.txt", "--from", "2023-01-01", "--to", "invalid-date", "--format", "adoc"};

        assertThrows(DateTimeParseException.class, () -> argumentsAnalyzer.argumentsAnalyze(args));
    }

    @Test
    void testInvalidFormatArgumentsAnalyze() {
        ArgumentsAnalyzer argumentsAnalyzer = new ArgumentsAnalyzer();
        String[] args = {"--path", "log.txt", "--from", "2023-01-01", "--to", "2023-12-31", "--format", "invalid-format"};

        assertDoesNotThrow(() -> argumentsAnalyzer.argumentsAnalyze(args));
    }

    @Test
    void testEmptyArgumentsAnalyze() {
        ArgumentsAnalyzer argumentsAnalyzer = new ArgumentsAnalyzer();
        String[] args = {};

        argumentsAnalyzer.argumentsAnalyze(args);

        assertNull(argumentsAnalyzer.getFrom());
        assertNull(argumentsAnalyzer.getTo());
        assertEquals(OutputFormat.MARKDOWN, argumentsAnalyzer.getFormat());
        assertTrue(argumentsAnalyzer.getSourceList().isEmpty());
    }
}
