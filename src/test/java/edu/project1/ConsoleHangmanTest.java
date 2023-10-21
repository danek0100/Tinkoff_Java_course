package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleHangmanTest {

    @Test
    @DisplayName("Test invalid word length from dictionary")
    public void testInvalidWordLength() {
        Dictionary invalidDictionary = new FileDictionary("./src/test/java/edu/project1/invalid_dict_1.txt");
        assertThrows(IllegalArgumentException.class, invalidDictionary::randomWord);
    }

    @Test
    @DisplayName("Test that exceeding max attempts results in defeat")
    public void testMaxAttemptsResultsInDefeat() {
        Session session = new Session("word");
        GuessResult result = null;
        for (int i = 0; i < 5; i++) {
            result = session.guess('z');
        }
        assertTrue(result instanceof GuessResult.PlayerDefeat);
    }

    @Test
    @DisplayName("Test that game state changes correctly on guessing")
    public void testGameStateChangesCorrectly() {
        Session session = new Session("word");
        GuessResult result = session.guess('o');
        assertTrue(result instanceof GuessResult.SuccessfulGuess);
        assertEquals("*o**", new String(result.state()));
    }

    @Test
    @DisplayName("Test input length greater than one results in exception")
    public void testInputLengthGreaterThanOne() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        System.setIn(in);

        InputReader reader = new InputReader();
        new Thread(() -> {
            try {
                out.write("wo\n".getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        assertThrows(IllegalArgumentException.class, reader::readUserSingleCharInput);
    }

    static class MockOutput implements Output {
        private final StringBuilder capturedOutput = new StringBuilder();

        @Override
        public void log(String message) {
            capturedOutput.append(message).append("\n");
        }

        public String getCapturedOutput() {
            return capturedOutput.toString();
        }
    }


    @Test
    @DisplayName("Test full game cycle with user inputs")
    public void testFullGameCycle() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        System.setIn(in);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        new Thread(() -> {
            try {
                out.write("h\n".getBytes());
                out.write("e\n".getBytes());
                out.write("l\n".getBytes());
                out.write("o\n".getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        MockOutput mockOutput = new MockOutput();
        ConsoleHangman game = new ConsoleHangman(new FileDictionary("./src/test/java/edu/project1/valid_dict_1.txt"), mockOutput);
        game.run();

        String output = mockOutput.getCapturedOutput();
        output = output.replaceAll("\\x1B\\[[;\\d]*m", "");
        String outputUtf8 = new String(output.getBytes(StandardCharsets.UTF_8));
        assertTrue(outputUtf8.contains("You won!"));
    }
}
