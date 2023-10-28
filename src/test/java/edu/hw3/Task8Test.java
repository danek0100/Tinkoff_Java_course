package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class Task8Test {

    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        Iterator<Integer> it = new Task8.BackwardIterator<>(Arrays.asList(1, 2, 3));
        assertTrue(it.hasNext());
        assertEquals(3, it.next());
        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("Edge Test Cases")
    void edgeTestCases() {
        Iterator<Integer> emptyIt = new Task8.BackwardIterator<>(List.of());
        assertFalse(emptyIt.hasNext());

        assertThrows(NoSuchElementException.class, emptyIt::next);

        Iterator<String> singleElementIt = new Task8.BackwardIterator<>(List.of("only"));
        assertTrue(singleElementIt.hasNext());
        assertEquals("only", singleElementIt.next());
        assertFalse(singleElementIt.hasNext());
    }
}
