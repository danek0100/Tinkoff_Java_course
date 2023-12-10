package edu.project4;


import edu.project4.components.Color;
import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.config.Config;
import edu.project4.config.PresetAffineTransformation;
import edu.project4.processors.ImageProcessor;
import edu.project4.processors.LogarithmicGammaCorrectionProcessor;
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

public class FractalFlameGenerator {

    private FractalFlameGenerator() {
    }

    public static void generateAndSaveFractal(Config config) {
        validate(config);

        List<ColorTransformation> affine = prepareAffine(
            config.getRandomAffineTransformationsCount(),
            config.getPresetAffineTransformations()
        );

        Renderer renderer;

        if (config.getThreadsCount() <= 1) {
            renderer = new SingleRenderer(config.getSymmetry());
        } else {
            //renderer = new MultithreadedRenderer(configObject.getThreadsCount());
            return;
        }

        FractalImage fractalImage = renderer.render(
            FractalImage.create(config.getWidth(), config.getHeight()),
            new Rect(-config.getWidth() / 2., -config.getHeight() / 2.,
                config.getWidth(), config.getHeight()),
            affine,
            Arrays.stream(config.getNonlinearTransformations()).toList(),
            config.getSamples(),
            config.getIterations(),
            config.getSeed()
        );

        if (config.isWithCorrection()) {
            ImageProcessor processor = new LogarithmicGammaCorrectionProcessor(config.getGamma());
            processor.process(fractalImage);
        }

        try {
            ImageUtils.save(
                fractalImage,
                Path.of(config.getDirectory(),
                    config.getFilename() + "." + config.getFileType().toString().toLowerCase()),
                config.getFileType()
            );
        } catch (IOException ignored) {} // TODO
    }


    private static List<ColorTransformation> prepareAffine(int randomCount, PresetAffineTransformation[] preset) {
        List<ColorTransformation> affine = new ArrayList<>();

        for (int i = 0; i < randomCount; i++) {
            affine.add(new ColorTransformation(
                LinearTransformation.randomTransformation(),
                Color.generate()
            ));
        }

        if (preset != null) {
            for (PresetAffineTransformation presetObj : preset) {
                affine.add(new ColorTransformation(
                    new LinearTransformation(
                        presetObj.getA(), presetObj.getB(), presetObj.getC(),
                        presetObj.getD(), presetObj.getE(), presetObj.getF()
                    ),
                    new Color(
                        presetObj.getRed(), presetObj.getGreen(), presetObj.getBlue()
                    )
                ));
            }
        }

        return affine;
    }

    private static void validate(Config config) {
        if (config.getWidth() <= 0) {
            throw new IllegalArgumentException("Неверная ширина изображения");
        }

        if (config.getHeight() <= 0) {
            throw new IllegalArgumentException("Неверная высота изображения");
        }

        if (config.getThreadsCount() <= 0) {
            throw new IllegalArgumentException("Неверное количество потоков");
        }

        if (config.getSamples() <= 0) {
            throw new IllegalArgumentException("Неверное количество повторений");
        }

        if (config.getIterations() <= 0) {
            throw new IllegalArgumentException("Неверное количество итераций");
        }

        if (config.getRandomAffineTransformationsCount() <= 0) {
            throw new IllegalArgumentException("Неверное количество случайных преобразований");
        }

        if (config.getRandomAffineTransformationsCount() == 0 && config.getPresetAffineTransformations().length == 0) {
            throw new IllegalArgumentException("Отсутствуют линейные преобразования");
        }

        if (config.getNonlinearTransformations().length == 0) {
            throw new IllegalArgumentException("Отсутствуют нелинейные преобразования");
        }

        if (config.getDirectory() == null) {
            throw new IllegalArgumentException("Отсутствует путь для сохранения файла");
        }
    }
}
