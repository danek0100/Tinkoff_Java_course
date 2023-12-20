package edu.project4.transformations;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NonLinearTransformationsTest {

    @Test
    public void getRandomTransformation_shouldReturnValidTransformation() {
        Transformation transformation = NonLinearTransformations.getRandomTransformation();
        assertThat(transformation).isNotNull();
        assertThat(transformation).isInstanceOf(Transformation.class);
    }

    @Test
    public void getByNumber_shouldReturnCorrectTransformation() {
        Transformation transformation = NonLinearTransformations.getByNumber(0);
        assertThat(transformation).isNotNull();
        assertThat(transformation).isInstanceOf(CylinderTransformation.class);
    }

    @Test
    public void getByNumber_withInvalidNumber_shouldThrowException() {
        int invalidNumber = NonLinearTransformations.values().length;
        assertThrows(IllegalArgumentException.class, () -> NonLinearTransformations.getByNumber(invalidNumber));
    }
}
