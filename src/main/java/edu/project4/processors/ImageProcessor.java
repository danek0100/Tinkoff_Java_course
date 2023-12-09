package edu.project4.processors;

import edu.project4.components.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
