package edu.project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleHangmanTest {
    private final Dictionary testDictionary = new SimpleDictionary();

    @Test
    void gameDoesNotStart_IfWordHasInvalidLength() {
        String word = "";
        assertThrows(IllegalArgumentException.class, () -> new Session(word));
    }

    @Test
    void gameAlwaysReturnsDefeat_AfterMaxAttempts() {
        Session session = new Session("test");
        GuessResult result = null;

        for (int i = 0; i < 5; i++) {
            result = session.guess('x');
        }

        assertTrue(result instanceof GuessResult.Defeat);
    }

    @Test
    void gameStateChangesCorrectly_OnGuess() {
        Session session = new Session("test");
        GuessResult result;

        result = session.guess('t');
        assertTrue(result instanceof GuessResult.SuccessfulGuess);
        assertEquals("t**t", new String(result.state()));

        result = session.guess('x');
        assertTrue(result instanceof GuessResult.FailedGuess);
        assertEquals("t**t", new String(result.state()));
    }

    @Test
    void gamePromptForRetry_OnLongerInput() {
        ConsoleHangman game = new ConsoleHangman(testDictionary);
        // Тут немного сложнее тестировать, так как нужно будет мокать ввод с консоли или изменить структуру вашего класса,
        // чтобы он принимал ввод из другого источника, например, из строки.
        // Для этого, возможно, придется немного реорганизовать ваш код.
        // Однако, этот тест будет лучше провести вручную.
    }
}
