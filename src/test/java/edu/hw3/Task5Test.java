package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task5Test {

    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        assertEquals(
            Arrays.asList(new Task5.Contact("Thomas Aquinas"), new Task5.Contact("Rene Descartes"), new Task5.Contact("David Hume"), new Task5.Contact("John Locke")),
            Task5.parseContacts(new String[]{"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"}, "ASC")
        );

        assertEquals(
            Arrays.asList(new Task5.Contact("Carl Gauss"), new Task5.Contact("Leonhard Euler"), new Task5.Contact("Paul Erdos")),
            Task5.parseContacts(new String[]{"Paul Erdos", "Leonhard Euler", "Carl Gauss"}, "DESC")
        );
    }

    @Test
    @DisplayName("Edge Test Cases")
    void edgeTestCases() {
        assertEquals(
            Arrays.asList(),
            Task5.parseContacts(new String[]{}, "DESC")
        );

        assertEquals(
            Arrays.asList(),
            Task5.parseContacts(null, "DESC")
        );

        assertEquals(
            Arrays.asList(new Task5.Contact("Paul Erdos"), new Task5.Contact("Leonhard Euler")),
            Task5.parseContacts(new String[]{"Paul Erdos", "Leonhard Euler"}, "ASC")
        );

        assertEquals(
            Arrays.asList(new Task5.Contact("Leonhard Euler"), new Task5.Contact("Paul Erdos")),
            Task5.parseContacts(new String[]{"Paul Erdos", "Leonhard Euler"}, "DESC")
        );
    }

    @Test
    @DisplayName("Only Name Test Cases")
    void onlyNameTestCases() {
        // Sorting with only names (ASC)
        assertEquals(
            Arrays.asList(new Task5.Contact("Alice"), new Task5.Contact("Bob"), new Task5.Contact("Charlie")),
            Task5.parseContacts(new String[]{"Charlie", "Alice", "Bob"}, "ASC")
        );

        // Sorting with only names (DESC)
        assertEquals(
            Arrays.asList(new Task5.Contact("Charlie"), new Task5.Contact("Bob"), new Task5.Contact("Alice")),
            Task5.parseContacts(new String[]{"Charlie", "Alice", "Bob"}, "DESC")
        );

        // Mixing names and full names (ASC)
        assertEquals(
            Arrays.asList(new Task5.Contact("Alice"), new Task5.Contact("Charlie"), new Task5.Contact("Bob Johnson"), new Task5.Contact("David Smith")),
            Task5.parseContacts(new String[]{"David Smith", "Charlie", "Alice", "Bob Johnson"}, "ASC")
        );

        // Mixing names and full names (DESC)
        assertEquals(
            Arrays.asList(new Task5.Contact("David Smith"), new Task5.Contact("Bob Johnson"), new Task5.Contact("Charlie"), new Task5.Contact("Alice")),
            Task5.parseContacts(new String[]{"David Smith", "Charlie", "Alice", "Bob Johnson"}, "DESC")
        );
    }

}
