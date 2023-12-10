package edu.project4.transformations;

import edu.project4.components.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LinearTransformationTest {

    @Test
    public void apply_shouldTransformPointCorrectly() {
        double a = 1, b = 0, c = 1, d = 0, e = 1, f = 1;
        LinearTransformation transformation = new LinearTransformation(a, b, c, d, e, f);

        Point originalPoint = new Point(2, 3);
        Point expectedTransformedPoint = new Point(3, 4);

        Point result = transformation.apply(originalPoint);

        assertThat(result).isEqualTo(expectedTransformedPoint);
    }

    @Test
    public void randomTransformation_shouldCreateTransformation() {
        LinearTransformation transformation = LinearTransformation.randomTransformation();
        assertThat(transformation).isNotNull();
    }
}
