package edu.project4.renderers;

import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.Point;
import edu.project4.components.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class SingleRenderer implements Renderer {

    private final int symmetry;

    public SingleRenderer(int symmetry) {
        this.symmetry = symmetry;
    }

    @Override
    public FractalImage render(FractalImage canvas, Rect world, List<Transformation> variations,
        int samples, short iterPerSample, long seed) {
        Random random = new Random(seed);

        for (int num = 0; num < samples; ++num) {
            Point pw = randomPoint(world, random);

            for (short step = 0; step < iterPerSample; ++step) {
                Transformation variation = variations.get(random.nextInt(variations.size()));
                pw = variation.apply(pw);

                for (int s = 0; s < symmetry; s++) {
                    double theta2 = Math.PI * 2 / symmetry * s;
                    Point pwr = rotate(pw, theta2);
                    if (!world.contains(pwr)) continue;

                    Pixel pixel = mapRangeToPixel(world, pwr, canvas);
                    if (pixel == null) continue;

                    int canvasX = (int) ((pwr.x() - world.x()) / world.width() * canvas.width());
                    int canvasY = (int) ((pwr.y() - world.y()) / world.height() * canvas.height());

                    if (canvas.contains(canvasX, canvasY)) {
                        updatePixel(canvas, pixel, canvasX, canvasY);
                    }

                }
            }
        }

        return canvas;
    }

    private Point randomPoint(Rect world, Random random) {
        double x = world.x() + random.nextDouble() * world.width();
        double y = world.y() + random.nextDouble() * world.height();
        return new Point(x, y);
    }

    private Point rotate(Point point, double theta) {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double newX = cosTheta * point.x() - sinTheta * point.y();
        double newY = sinTheta * point.x() + cosTheta * point.y();

        return new Point(newX, newY);
    }


    private Pixel mapRangeToPixel(Rect world, Point point, FractalImage canvas) {
        int canvasX = (int) Math.round((point.x() - world.x()) / world.width() * canvas.width());
        int canvasY = (int) Math.round((point.y() - world.y()) / world.height() * canvas.height());

        if (canvasX == canvas.width()) {
            canvasX -= 1;
        }
        if (canvasY == canvas.height()) {
            canvasY -= 1;
        }

        if (canvas.contains(canvasX, canvasY)) {
            return canvas.pixel(canvasX, canvasY);
        }

        return null;
    }


    private void updatePixel(FractalImage canvas, Pixel pixel, int x, int y) {
        int newHitCount = pixel.hitCount() + 1;

        int newR = (newHitCount * 64) % 256;
        int newG = (newHitCount * 128) % 256;
        int newB = (newHitCount * 192) % 256;

        canvas.data()[y * canvas.width() + x] = new Pixel(newR, newG, newB, newHitCount);
    }


}
