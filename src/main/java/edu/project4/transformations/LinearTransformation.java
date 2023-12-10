package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Random;

/**
 * Represents a linear transformation applied to a point in a fractal rendering.
 */
public class LinearTransformation implements Transformation {
    private static final int BORDER = 2;
    private static final Random RANDOM = new Random();

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    /**
     * Constructs a linear transformation with the specified coefficients.
     *
     * @param a The coefficient 'a'.
     * @param b The coefficient 'b'.
     * @param c The coefficient 'c'.
     * @param d The coefficient 'd'.
     * @param e The coefficient 'e'.
     * @param f The coefficient 'f'.
     */
    public LinearTransformation(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    /**
     * Apply this linear transformation to a given point.
     *
     * @param point The input point to transform.
     * @return The transformed point.
     */
    @Override
    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;
        return new Point(x, y);
    }

    /**
     * Generate a random linear transformation.
     *
     * @return A randomly generated linear transformation.
     */
    @SuppressWarnings("MultipleVariableDeclarations")
    public static LinearTransformation randomTransformation() {
        double a, b, c, d, e, f;

        do {
            a = nextRandomCoefficient();
            d = nextRandomCoefficientWithCondition(a);

            b = nextRandomCoefficient();
            e = nextRandomCoefficientWithCondition(b);
        } while (!isValidCombination(a, b, d, e));

        c = RANDOM.nextDouble(-BORDER, BORDER);
        f = RANDOM.nextDouble(-BORDER, BORDER);

        return new LinearTransformation(a, b, c, d, e, f);
    }

    private static double nextRandomCoefficient() {
        return RANDOM.nextDouble() * 2 - 1;
    }

    private static double nextRandomCoefficientWithCondition(double coeff) {
        double result;
        do {
            result = Math.sqrt(RANDOM.nextDouble()) * (RANDOM.nextBoolean() ? 1 : -1);
        } while (coeff * coeff + result * result > 1);
        return result;
    }

    private static boolean isValidCombination(double a, double b, double d, double e) {
        return (a * a + b * b + d * d + e * e) <= (1 + (a * e - d * b) * (a * e - d * b));
    }
}
