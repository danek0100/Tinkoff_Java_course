package edu.project4.components;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RectTest {

    @Test
    public void contains_shouldReturnTrueForPointInsideRectangle() {
        Rect rect = new Rect(0, 0, 10, 10);
        Point pointInside = new Point(5, 5);

        assertThat(rect.contains(pointInside)).isTrue();
    }

    @Test
    public void contains_shouldReturnFalseForPointOutsideRectangle() {
        Rect rect = new Rect(0, 0, 10, 10);
        Point pointOutside = new Point(11, 11);

        assertThat(rect.contains(pointOutside)).isFalse();
    }

    @Test
    public void contains_shouldReturnTrueForPointOnEdgeOfRectangle() {
        Rect rect = new Rect(0, 0, 10, 10);
        Point pointOnEdge = new Point(0, 0);

        assertThat(rect.contains(pointOnEdge)).isTrue();
    }

    @Test
    public void contains_shouldReturnFalseForPointOnBorderOutsideRectangle() {
        Rect rect = new Rect(0, 0, 10, 10);
        Point pointOnBorderOutside = new Point(11, 11);

        assertThat(rect.contains(pointOnBorderOutside)).isFalse();
    }
}
