package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {

    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        TreeMap<String, String> tree = new TreeMap<>(new Task7.NullFriendlyComparator<>());

        // Adding null key
        tree.put(null, "test");

        // Adding some other keys
        tree.put("b", "value_b");
        tree.put("a", "value_a");

        // Check if null key exists
        assertTrue(tree.containsKey(null));
        assertEquals("test", tree.get(null));

        // Check for other keys
        assertEquals("value_a", tree.get("a"));
        assertEquals("value_b", tree.get("b"));
    }

    @Test
    @DisplayName("Edge Test Cases")
    void edgeTestCases() {
        TreeMap<String, String> tree = new TreeMap<>(new Task7.NullFriendlyComparator<>());

        // Adding null key
        tree.put(null, "test");

        // Check if null key exists
        assertTrue(tree.containsKey(null));
        assertEquals("test", tree.get(null));

        // Removing null key
        tree.remove(null);
        assertNull(tree.get(null));
    }
}
