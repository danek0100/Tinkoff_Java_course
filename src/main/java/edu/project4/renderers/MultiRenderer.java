package edu.project4.renderers;

import edu.project4.components.Color;
import edu.project4.components.FractalImage;
import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.Point;
import edu.project4.components.Rect;
import edu.project4.transformations.ColorTransformation;
import edu.project4.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Multi-threaded renderer for fractal images with various transformations and parameters.
 */
public class MultiRenderer implements Renderer {

    private final int symmetry;
    private final int threadCount;
    private final static Logger LOGGER = LogManager.getLogger();

    /**
     * Constructs a MultiRenderer with the specified number of threads and symmetry factor.
     *
     * @param threadCount The number of threads to use for rendering.
     * @param symmetry    The symmetry factor for fractal rendering.
     */
    public MultiRenderer(int threadCount, int symmetry) {
        this.threadCount = threadCount;
        this.symmetry = symmetry;
    }

    /**
     * Renders a fractal image on the specified canvas within the given world coordinates.
     *
     * @param canvas        The canvas to render the image on.
     * @param world         The rectangular region in world coordinates to render.
     * @param affine        The list of color transformations to apply.
     * @param variations    The list of transformations to apply.
     * @param samples       The number of samples per pixel.
     * @param iterPerSample The number of iterations per sample.
     * @param seed          The random seed for rendering.
     * @return              The rendered fractal image.
     */
    @Override
    public IFractalImage render(
        IFractalImage canvas, Rect world, List<ColorTransformation> affine,
        List<Transformation> variations, int samples, int iterPerSample, int seed) {

        List<Callable<IFractalImage>> tasks = new ArrayList<>();
        int samplesPerThread = samples / threadCount;
        int remainingSamples = samples % threadCount;

        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < threadCount; i++) {
                int finalI = i;
                tasks.add(() -> renderPart(
                    FractalImage.create(canvas.getWidth(), canvas.getHeight()), world, affine,
                    variations, samplesPerThread + ((finalI < remainingSamples) ? 1 : 0), iterPerSample));
            }

            try {
                List<Future<IFractalImage>> futures = executorService.invokeAll(tasks);

                for (Future<IFractalImage> future : futures) {
                    merge(canvas, future.get());
                }
            } catch (Exception exception) {
                LOGGER.error(exception);
            }
        }
        return canvas;
    }

    private IFractalImage renderPart(IFractalImage canvas, Rect world, List<ColorTransformation> affine,
        List<Transformation> variations, int samples, int iterPerSample) {
        for (int i = 0; i < samples; i++) {
            Point pw = randomPoint(world);

            for (int step = 0; step < iterPerSample; ++step) {
                ColorTransformation chosenAffine = affine.get(ThreadLocalRandom.current().nextInt(affine.size()));
                Transformation variation = variations.get(ThreadLocalRandom.current().nextInt(variations.size()));

                pw = variation.apply(chosenAffine.transformation().apply(pw));

                if (symmetry > 0) {
                    for (int s = 0; s < symmetry; s++) {
                        double theta = Math.PI * 2 / symmetry * s;
                        Point pwr = rotate(pw, theta);

                        applyChanges(canvas, world, pwr, chosenAffine);
                    }
                } else {
                    applyChanges(canvas, world, pw, chosenAffine);
                }
            }
        }
        return canvas;
    }

    private void applyChanges(IFractalImage canvas, Rect world, Point pw, ColorTransformation chosenAffine) {
        if (world.contains(pw)) {
            int canvasX = extension(canvas.getWidth(), world.x(), world.x() + world.width(), pw.x());
            int canvasY = extension(canvas.getHeight(), world.y(), world.y() + world.height(), pw.y());

            if (canvas.contains(canvasX, canvasY)) {
                updatePixel(canvas, chosenAffine, canvasX, canvasY);
            }
        }
    }

    private Point randomPoint(Rect world) {

        double x = world.x() + (ThreadLocalRandom.current().nextDouble() * world.width());
        double y = world.y() + (ThreadLocalRandom.current().nextDouble() * world.height());

        return new Point(x, y);
    }

    private Point rotate(Point point, double theta) {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double newX = cosTheta * point.x() - sinTheta * point.y();
        double newY = sinTheta * point.x() + cosTheta * point.y();

        return new Point(newX, newY);
    }

    private Color mixColor(Color first, Color second) {
        return new Color(
            (first.r() + second.r()) / 2,
            (first.g() + second.g()) / 2,
            (first.b() + second.b()) / 2
        );
    }

    private int extension(int size, double min, double max, double point) {
        return size - (int) Math.ceil(
            (max - point) / (max - min) * size
        );
    }

    private void updatePixel(IFractalImage canvas, ColorTransformation colorTransformation, int x, int y) {
        Pixel oldPixel = canvas.pixel(x, y);
        Color newColor = mixColor(oldPixel.color(), colorTransformation.color());
        int newHitCount = oldPixel.hitCount() + 1;
        canvas.updatePixel(x, y, new Pixel(newColor, newHitCount));
    }

    private void merge(IFractalImage mainCanvas, IFractalImage partialCanvas) {
        int width = mainCanvas.getWidth();
        int height = mainCanvas.getHeight();

        Pixel[] partialData = partialCanvas.getData();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                Pixel mainPixel = mainCanvas.pixel(x, y);
                Pixel partialPixel = partialData[index];

                if (partialPixel != null) {
                    Pixel mergedPixel = mergePixels(mainPixel, partialPixel);
                    mainCanvas.updatePixel(x, y, mergedPixel);
                }
            }
        }
    }

    private Pixel mergePixels(Pixel pixel1, Pixel pixel2) {
        int mergedR = (pixel1.color().r() + pixel2.color().r()) / 2;
        int mergedG = (pixel1.color().g() + pixel2.color().g()) / 2;
        int mergedB = (pixel1.color().b() + pixel2.color().b()) / 2;
        int mergedHitCount = pixel1.hitCount() + pixel2.hitCount();

        return new Pixel(new Color(mergedR, mergedG, mergedB), mergedHitCount);
    }
}
