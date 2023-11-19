package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link Task8}.
 */
public class Task8Test {

    @Test
    @DisplayName("Is odd length")
    public void testIsOddLength() {
        assertThat(Task8.isOddLength("1")).isTrue();
        assertThat(Task8.isOddLength("101")).isTrue();
        assertThat(Task8.isOddLength("00")).isFalse();
        assertThat(Task8.isOddLength("10101")).isTrue();
    }

    @Test
    @DisplayName("Starts with 0 odd or 1 even")
    public void testStartsWith0OddOr1Even() {
        assertThat(Task8.startsWith0OddOr1Even("0")).isTrue();
        assertThat(Task8.startsWith0OddOr1Even("11")).isTrue();
        assertThat(Task8.startsWith0OddOr1Even("101")).isFalse();
        assertThat(Task8.startsWith0OddOr1Even("1010")).isTrue();
    }

    @Test
    @DisplayName("Zero multiple of three")
    public void testZeroMultipleOfThree() {
        assertThat(Task8.zeroMultipleOfThree("000")).isTrue();
        assertThat(Task8.zeroMultipleOfThree("1001")).isFalse();
        assertThat(Task8.zeroMultipleOfThree("010010")).isFalse();
        assertThat(Task8.zeroMultipleOfThree("0100101011110")).isTrue();
    }

    @Test
    @DisplayName("Not 11 or 111")
    public void testNot11Or111() {
        assertThat(Task8.not11Or111("11")).isFalse();
        assertThat(Task8.not11Or111("111")).isFalse();
        assertThat(Task8.not11Or111("101")).isTrue();
        assertThat(Task8.not11Or111("000")).isTrue();
    }

    @Test
    @DisplayName("Odd position is one")
    public void testOddPositionIsOne() {
        assertThat(Task8.oddPositionIsOne("1")).isTrue();
        assertThat(Task8.oddPositionIsOne("101")).isTrue();
        assertThat(Task8.oddPositionIsOne("1010")).isTrue();
        assertThat(Task8.oddPositionIsOne("010")).isFalse();
    }

    @Test
    @DisplayName("At least two zeros at most one one")
    public void testAtLeastTwoZerosAtMostOneOne() {
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("00")).isFalse();
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("010")).isTrue();
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("001")).isTrue();
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("100")).isTrue();
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("110")).isFalse();
        assertThat(Task8.atLeastTwoZerosAtMostOneOne("111")).isFalse();
    }

    @Test
    @DisplayName("No consecutive ones")
    public void testNoConsecutiveOnes() {
        assertThat(Task8.noConsecutiveOnes("10101")).isTrue();
        assertThat(Task8.noConsecutiveOnes("110")).isFalse();
        assertThat(Task8.noConsecutiveOnes("0")).isTrue();
        assertThat(Task8.noConsecutiveOnes("11")).isFalse();
    }
}
