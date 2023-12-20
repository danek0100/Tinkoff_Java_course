package edu.project4.renderers;

import edu.project4.components.Color;
import edu.project4.components.IFractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.Point;
import edu.project4.components.Rect;
import edu.project4.transformations.ColorTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Renderer that performs parallel rendering of fractal images using multiple threads.
 */
public class ParallelRenderer implements Renderer {

    private final int symmetry;
    private final int threadCount;

    /**
     * Constructs a ParallelRenderer with the specified number of threads and symmetry factor.
     *
     * @param threadCount The number of threads to use for rendering.
     * @param symmetry    The symmetry factor for fractal rendering.
     */
    public ParallelRenderer(int threadCount, int symmetry) {
        this.threadCount = threadCount;
        this.symmetry = symmetry;
    }

    /**
     * Renders a fractal image on the specified canvas within the given world coordinates using parallel processing.
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
    public IFractalImage render(IFractalImage canvas, Rect world, List<ColorTransformation> affine,
        List<Transformation> variations, int samples, int iterPerSample, int seed) {
        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < samples; i++) {
                executor.submit(() -> renderSample(canvas, world, affine, variations, iterPerSample));
            }
        }
        return canvas;
    }

    private void renderSample(IFractalImage canvas, Rect world, List<ColorTransformation> affine,
        List<Transformation> variations, int iterPerSample) {
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

    private Point randomPoint(Rect world) {
        double x = world.x() + (ThreadLocalRandom.current().nextDouble() * world.width());
        double y = world.y() + (ThreadLocalRandom.current().nextDouble() * world.height());

        return new Point(x, y);
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
}
