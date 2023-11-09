package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {

    @Test
    @DisplayName("Basic Encoding and Decoding")
    void basicEncodingDecodingTest() {
        assertEquals("Svool dliow!", Task1.atbash("Hello world!"));
        assertEquals("Hello world!", Task1.atbash("Svool dliow!"));

        String quote = "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler";
        String encodedQuote = "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi";

        assertEquals(encodedQuote, Task1.atbash(quote));
        assertEquals(quote, Task1.atbash(encodedQuote));
    }

    @Test
    @DisplayName("Non-Latin Characters")
    void testNonLatinCharacters() {
        assertEquals("R gsrmp gszg Qzez - это просто", Task1.atbash("I think that Java - это просто"));
    }

    @Test
    @DisplayName("Mix of Uppercase and Lowercase")
    void testMixedCase() {
        assertEquals("ZMB xzg XZM DIRGV XLWV", Task1.atbash("ANY cat CAN WRITE CODE"));
    }
}
