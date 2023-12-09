package edu.project4;


import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.processors.ImageProcessor;
import edu.project4.processors.LogarithmicGammaCorrectionProcessor;
import edu.project4.renderers.SingleRenderer;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.HeartTransformation;
import edu.project4.transformations.LinearTransformation;
import edu.project4.transformations.PolarTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphericalTransformation;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ImageUtils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FractalFlameGenerator {

    public static void generateAndSaveFractalImage(int width, int height, int samples, short iterPerSample, long seed,
        int symmetry, Path outputPath, ImageFormat format) {
        Rect world = new Rect(-width / 2., -height / 2., width, height);
        FractalImage canvas = FractalImage.create(width, height);

        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new LinearTransformation(1.0, 1.0, 50.0, 50.0));
        transformations.add(new SinusoidalTransformation());
        transformations.add(new DiskTransformation());
        transformations.add(new PolarTransformation());
        transformations.add(new SphericalTransformation());
        transformations.add(new HeartTransformation());


        SingleRenderer renderer = new SingleRenderer(symmetry);
        FractalImage renderedImage = renderer.render(canvas, world, transformations, samples, iterPerSample, seed);

        ImageProcessor processor = new LogarithmicGammaCorrectionProcessor(2.2);
        processor.process(renderedImage);

        try {
            ImageUtils.save(renderedImage, outputPath, format);
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }
    }
}
