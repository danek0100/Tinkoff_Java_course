package edu.project4.components;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ColorTest {

    @Test
    public void generate_shouldReturnColorWithRGBInRange() {
        Color color = Color.generate();

        assertThat(color.r()).isBetween(0, 255);
        assertThat(color.g()).isBetween(0, 255);
        assertThat(color.b()).isBetween(0, 255);
    }
}
