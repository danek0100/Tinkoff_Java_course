package edu.hw2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {

    @Test
    @DisplayName("Calling Info should return correct method names (testCallingInfo)")
    public void testCallingInfo() {
        Task4.CallingInfo info = Task4.callingInfo();

        assertThat(info).isNotNull();
        assertThat(info.methodName()).isEqualTo("testCallingInfo");
    }

    @Test
    @DisplayName("Calling Info should return correct method names (anotherTestMethod)")
    public void anotherTestMethod() {
        Task4.CallingInfo info = Task4.callingInfo();

        assertThat(info).isNotNull();
        assertThat(info.methodName()).isEqualTo("anotherTestMethod");
    }
}
