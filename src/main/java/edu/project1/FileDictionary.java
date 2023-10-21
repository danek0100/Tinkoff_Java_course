package edu.project1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of Dictionary that fetches words from a file.
 */
public class FileDictionary implements Dictionary {
    private final List<String> dictionary;
    private final Random random = new Random();

    /**
     * Constructor that initializes the dictionary from a file.
     *
     * @param filename The name of the file containing the dictionary words.
     */
    public FileDictionary(String filename) {
        this.dictionary = loadWordsFromFile(filename);
    }

    /**
     * Loads words from a file into a list.
     *
     * @param filename The name of the file containing the dictionary words.
     * @return List of words.
     */
    private List<String> loadWordsFromFile(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException("Error reading dictionary file: " + filename, e);
        }
    }

    @Override
    public @NotNull String randomWord() {
        if (dictionary.isEmpty()) {
            throw new IllegalStateException("Dictionary is empty!");
        }
        String word = dictionary.get(random.nextInt(dictionary.size()));
        checkWord(word);
        return word;
    }
}
