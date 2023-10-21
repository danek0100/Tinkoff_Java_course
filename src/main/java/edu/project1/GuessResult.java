package edu.project1;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the result of a guess in the Hangman game.
 */
sealed interface GuessResult {

    /**
     * Gets the current state of the word being guessed.
     *
     * @return The word's current state.
     */
    char[] state();

    /**
     * Gets the current number of incorrect attempts.
     *
     * @return The number of incorrect attempts.
     */
    int attempt();

    /**
     * Gets the maximum number of attempts allowed.
     *
     * @return The maximum number of attempts.
     */
    int maxAttempts();

    /**
     * Gets the message describing the result of the guess.
     *
     * @return The result message.
     */
    @NotNull String message();

    /**
     * Represents a result where the player has been defeated.
     */
    record PlayerDefeat(char[] state, int attempt, int maxAttempts, @NotNull String message) implements GuessResult {}

    /**
     * Represents a result where the player has won.
     */
    record PlayerWin(char[] state, int attempt, int maxAttempts, @NotNull String message) implements GuessResult {}

    /**
     * Represents a result where the player has made a successful guess.
     */
    record SuccessfulGuess(char[] state, int attempt, int maxAttempts, @NotNull String message) implements
        GuessResult {}

    /**
     * Represents a result where the player has made an incorrect guess.
     */
    record IncorrectGuess(char[] state, int attempt, int maxAttempts, @NotNull String message) implements GuessResult {}
}
