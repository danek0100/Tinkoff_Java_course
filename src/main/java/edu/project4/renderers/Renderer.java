package edu.project4.renderers;

import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;

public interface Renderer {
    FractalImage render(FractalImage canvas, Rect world, List<Transformation> variations, int samples,
        short iterPerSample, long seed);
}
