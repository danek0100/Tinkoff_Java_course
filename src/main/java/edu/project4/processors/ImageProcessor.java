package edu.project4.processors;

import edu.project4.components.IFractalImage;

/**
 * Functional interface for processing fractal images.
 */
@FunctionalInterface
public interface ImageProcessor {

    /**
     * Process the given fractal image.
     *
     * @param image The fractal image to be processed.
     */
    void process(IFractalImage image);
}
