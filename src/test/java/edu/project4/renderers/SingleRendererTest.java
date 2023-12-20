package edu.project4.renderers;

import edu.project4.components.Color;
import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.transformations.ColorTransformation;
import edu.project4.transformations.HeartTransformation;
import edu.project4.transformations.LinearTransformation;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleRendererTest {
    @Test
    void parallelRenderTest() {
        Renderer renderer = new ParallelRenderer(3, 0);

        var image = renderer.render(
            FractalImage.create(10, 10),
            new Rect(-1, -1, 2, 2),
            List.of(new ColorTransformation(
                LinearTransformation.randomTransformation(),
                Color.generate()
            )),
            List.of(new HeartTransformation()),
            20,
            20,
            0
        );

        assertThat(image).isNotNull();
    }
}
