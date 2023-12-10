package edu.project4;

import edu.project4.components.AffineTransformation;
import edu.project4.components.Color;
import edu.project4.components.FractalImage;
import edu.project4.components.IFractalImage;
import edu.project4.components.Rect;
import edu.project4.components.SyncFractalImage;
import edu.project4.config.Config;
import edu.project4.processors.GammaCorrectionProcessor;
import edu.project4.processors.ImageProcessor;
import edu.project4.renderers.ParallelRenderer;
import edu.project4.renderers.Renderer;
import edu.project4.renderers.SingleRenderer;
import edu.project4.transformations.ColorTransformation;
import edu.project4.transformations.LinearTransformation;
import edu.project4.utils.ImageUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class generates and saves fractal flame images based on the provided configuration.
 */
public class FractalFlameGenerator {

    private final static Logger LOGGER = LogManager.getLogger();

    private FractalFlameGenerator() {
    }


    /**
     * Generate and save a fractal flame image based on the provided configuration.
     *
     * @param config The configuration specifying the parameters of the fractal flame generation.
     */
    public static void generateAndSaveFractal(Config config) {
        List<ColorTransformation> affine = prepareAffine(
            config.getRandomAffineTransformationsCount(),
            config.getPresetAffineTransformations()
        );

        Renderer renderer;
        IFractalImage imageBase;

        if (config.getThreadsCount() <= 1) {
            renderer = new SingleRenderer(config.getSymmetry());
            imageBase = FractalImage.create(config.getWidth(), config.getHeight());
        } else {
            renderer = new ParallelRenderer(config.getThreadsCount(), config.getSymmetry());
            imageBase = SyncFractalImage.create(config.getWidth(), config.getHeight());
        }

        IFractalImage fractalImage = renderer.render(
            imageBase,
            new Rect(
                config.getMinX(), config.getMinY(),
                config.getMaxX() - config.getMinX(), config.getMaxY() - config.getMinY()),
            affine,
            Arrays.stream(config.getNonlinearTransformations()).toList(),
            config.getSamples(),
            config.getIterations(),
            config.getSeed()
        );

        if (config.isWithCorrection()) {
            ImageProcessor processor = new GammaCorrectionProcessor(config.getGamma());
            processor.process(fractalImage);
        }

        try {
            ImageUtils.save(
                fractalImage,
                Path.of(config.getDirectory(),
                    config.getFilename() + "." + config.getFileType().toString().toLowerCase()),
                config.getFileType()
            );
        } catch (IOException exception) {
            LOGGER.error(exception);
        }
    }


    private static List<ColorTransformation> prepareAffine(int randomCount, AffineTransformation[] preset) {
        List<ColorTransformation> affine = new ArrayList<>();

        for (int i = 0; i < randomCount; i++) {
            affine.add(new ColorTransformation(
                LinearTransformation.randomTransformation(),
                Color.generate()
            ));
        }

        if (preset != null) {
            for (AffineTransformation presetObj : preset) {
                affine.add(new ColorTransformation(
                    new LinearTransformation(
                        presetObj.a(), presetObj.b(), presetObj.c(),
                        presetObj.d(), presetObj.e(), presetObj.f()
                    ),
                    new Color(
                        presetObj.red(), presetObj.green(), presetObj.blue()
                    )
                ));
            }
        }

        return affine;
    }
}
