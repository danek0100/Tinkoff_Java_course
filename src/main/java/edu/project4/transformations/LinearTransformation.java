package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Random;

public class LinearTransformation implements Transformation {
    private static final int BORDER = 2;
    private static final Random rand = new Random();

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    public LinearTransformation(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;
        return new Point(x, y);
    }

    public static LinearTransformation randomTransformation() {
        double a, b, c, d, e, f;

        do {
            a = nextRandomCoefficient();
            d = nextRandomCoefficientWithCondition(a);

            b = nextRandomCoefficient();
            e = nextRandomCoefficientWithCondition(b);
        } while (!isValidCombination(a, b, d, e));

        c = rand.nextDouble(-BORDER, BORDER);
        f = rand.nextDouble(-BORDER, BORDER);

        return new LinearTransformation(a, b, c, d, e, f);
    }

    private static double nextRandomCoefficient() {
        return rand.nextDouble() * 2 - 1;
    }

    private static double nextRandomCoefficientWithCondition(double coeff) {
        double result;
        do {
            result = Math.sqrt(rand.nextDouble()) * (rand.nextBoolean() ? 1 : -1);
        } while (coeff * coeff + result * result > 1);
        return result;
    }


    private static boolean isValidCombination(double a, double b, double d, double e) {
        return (a * a + b * b + d * d + e * e) <= (1 + (a * e - d * b) * (a * e - d * b));
    }
}
