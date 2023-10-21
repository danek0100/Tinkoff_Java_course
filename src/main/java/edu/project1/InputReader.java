package edu.project1;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Provides utility methods for reading user input from the console.
 */
public class InputReader {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads the user's input as a single string.
     *
     * @return User's input.
     * @throws IllegalArgumentException if there's an error reading input.
     */
    public String readUserInput() throws IllegalArgumentException {
        return scanner.nextLine().trim();
    }

    /**
     * Reads a single character from the user's input.
     *
     * @return User's input character as a string.
     * @throws IllegalArgumentException if the input length is not 1 or there's an error reading input.
     */
    public String readUserSingleCharInput() throws IllegalArgumentException {
        try {
            String input = scanner.nextLine().trim();
            if (input.length() != 1) {
                throw new IllegalArgumentException("Please enter only a single character!");
            }
            return input;
        } catch (NoSuchElementException e) {
            return "give_up";
        }
    }
}
