package edu.project1;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;


/**
 * Represents a game session of Hangman.
 */
public class Session {
    private final String answer;
    private final char[] userAnswer;
    private static final int MAX_ATTEMPTS = 5;
    private int attempts = 0;

    /**
     * Initializes a new session with the provided answer.
     *
     * @param answer The word to be guessed.
     */
    public Session(String answer) {
        this.answer = answer;
        this.userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, '*');
    }

    /**
     * Makes a guess based on the provided character.
     *
     * @param guess The character to guess.
     * @return The result of the guess.
     */
    @NotNull
    public GuessResult guess(char guess) {
        if (answer.indexOf(guess) != -1) {
            revealGuessedCharacter(guess);
            if (isWordComplete()) {
                return new GuessResult.PlayerWin(userAnswer, attempts, MAX_ATTEMPTS, "You won!");
            }
            return new GuessResult.SuccessfulGuess(userAnswer, attempts, MAX_ATTEMPTS, "Hit!");
        } else {
            attempts++;
            if (attempts == MAX_ATTEMPTS) {
                return new GuessResult.PlayerDefeat(userAnswer, attempts, MAX_ATTEMPTS, "You lost!");
            }
            return new GuessResult.IncorrectGuess(userAnswer, attempts, MAX_ATTEMPTS,
                String.format("Missed, mistake %d out of %d.", attempts, MAX_ATTEMPTS));
        }
    }

    /**
     * Gives up the current game session.
     *
     * @return The result indicating the player gave up.
     */
    @NotNull
    public GuessResult giveUp() {
        return new GuessResult.PlayerDefeat(userAnswer, attempts, MAX_ATTEMPTS, "You gave up!");
    }

    private void revealGuessedCharacter(char guess) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == guess) {
                userAnswer[i] = guess;
            }
        }
    }

    private boolean isWordComplete() {
        return new String(userAnswer).equals(answer);
    }
}
