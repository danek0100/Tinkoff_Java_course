package edu.hw2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Task2.Rectangle(1, 1)),
            Arguments.of(new Task2.Square(1))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Test")
    void rectangleArea(Task2.Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(rect instanceof Task2.Square ? 100 : 200.0);
    }

    @Test
    @DisplayName("Negative Test: Incorrect area calculation for Rectangle")
    void negativeTestRectangleArea() {
        Task2.Rectangle rect = new Task2.Rectangle(10, 5);
        rect = rect.setWidth(15);

        assertThat(rect.area()).isNotEqualTo(50.0);
    }

    @Test
    @DisplayName("Negative Test: Incorrect area calculation for Square")
    void negativeTestSquareArea() {
        Task2.Square square = new Task2.Square(10);
        square = square.setWidth(15);

        assertThat(square.area()).isNotEqualTo(100.0);
    }


}
