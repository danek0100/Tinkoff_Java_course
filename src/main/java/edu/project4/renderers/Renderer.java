package edu.project4.renderers;

import edu.project4.components.IFractalImage;
import edu.project4.components.Rect;
import edu.project4.transformations.ColorTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;

/**
 * Interface for rendering fractal images with various transformations and parameters.
 */
public interface Renderer {

    /**
     * Renders a fractal image on the specified canvas within the given world coordinates.
     *
     * @param canvas              The canvas to render the image on.
     * @param world               The rectangular region in world coordinates to render.
     * @param affine              The list of color transformations to apply.
     * @param variations          The list of transformations to apply.
     * @param samples             The number of samples per pixel.
     * @param iterPerSample       The number of iterations per sample.
     * @param seed                The random seed for rendering.
     * @return                    The rendered fractal image.
     */
    IFractalImage render(
        IFractalImage canvas,
        Rect world,
        List<ColorTransformation> affine,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int seed
    );
}
