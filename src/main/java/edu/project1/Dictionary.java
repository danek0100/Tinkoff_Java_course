package edu.project1;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a dictionary source from which words can be fetched.
 */
interface Dictionary {

    /**
     * Fetches a random word from the dictionary.
     *
     * @return A random word.
     * @throws IllegalArgumentException if a word cannot be fetched (e.g., empty dictionary).
     */
    @NotNull
    String randomWord() throws IllegalArgumentException;
}
