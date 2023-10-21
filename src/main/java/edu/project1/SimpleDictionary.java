package edu.project1;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a simple dictionary that provides words from a predefined list.
 */
class SimpleDictionary implements Dictionary {

    private static final int MAX_WORD_LENGTH = 20;
    private static final int MIN_WORD_LENGTH = 2;
    private static final String[] WORDS = {
        "hello", "world", "java", "hangman", "programming"
    };
    private static final Random RANDOM = new Random();

    /**
     * Fetches a random word from the predefined list.
     *
     * @return A random word.
     * @throws IllegalArgumentException if the chosen word's length is outside the specified bounds.
     */
    @Override
    public @NotNull String randomWord() throws IllegalArgumentException {
        String word = WORDS[RANDOM.nextInt(WORDS.length)];

        if (word.length() < MIN_WORD_LENGTH || word.length() > MAX_WORD_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Invalid word length for '%s'. Length must be between %d and %d.",
                    word, MIN_WORD_LENGTH, MAX_WORD_LENGTH)
            );
        }
        return word;
    }
}
