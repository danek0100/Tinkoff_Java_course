package edu.project1;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a dictionary source from which words can be fetched.
 */
interface Dictionary {

    int MAX_WORD_LENGTH = 20;
    int MIN_WORD_LENGTH = 2;

    /**
     * Fetches a random word from the dictionary.
     *
     * @return A random word.
     * @throws IllegalArgumentException if a word cannot be fetched (e.g., empty dictionary).
     */
    @NotNull
    String randomWord() throws IllegalArgumentException;

    default void checkWord(String word) throws IllegalArgumentException {
        if (word.length() < MIN_WORD_LENGTH || word.length() > MAX_WORD_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Invalid word length for '%s'. Length must be between %d and %d.",
                    word, MIN_WORD_LENGTH, MAX_WORD_LENGTH)
            );
        }
    }
}
