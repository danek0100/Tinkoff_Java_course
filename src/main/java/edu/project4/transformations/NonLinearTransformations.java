package edu.project4.transformations;

import java.util.Random;

/**
 * An enumeration of nonlinear transformations that can be applied in fractal rendering.
 */
public enum NonLinearTransformations {
    CYLINDER(new CylinderTransformation()),
    DIAMOND(new DiamondTransformation()),
    DISC(new DiskTransformation()),
    FISHEYE(new FisheyeTransformation()),
    HANDKERCHIEF(new HandkerchiefTransformation()),
    HEART(new HeartTransformation()),
    HORSESHOE(new HorseshoeTransformation()),
    HYPERBOLIC(new HyperbolicTransformation()),
    POLAR(new PolarTransformation()),
    SPHERICAL(new SphericalTransformation()),
    SPIRAL(new SpiralTransformation()),
    SWIRL(new SwirlTransformation()),
    SINUSOIDAL(new SinusoidalTransformation());

    private final Transformation transformation;

    /**
     * Constructs a NonLinearTransformations enum value with the associated transformation.
     *
     * @param transformation The transformation associated with this enum value.
     */
    NonLinearTransformations(Transformation transformation) {
        this.transformation = transformation;
    }

    /**
     * Get the transformation associated with this enum value.
     *
     * @return The transformation instance.
     */
    public Transformation getTransformation() {
        return transformation;
    }

    /**
     * Get a random transformation from the available nonlinear transformations.
     *
     * @return A randomly selected transformation.
     */
    public static Transformation getRandomTransformation() {
        return values()[new Random().nextInt(values().length)].getTransformation();
    }

    /**
     * Get a specific transformation by its ordinal number.
     *
     * @param number The ordinal number of the transformation.
     * @return The transformation corresponding to the given ordinal number.
     * @throws IllegalArgumentException if the number is out of range.
     */
    public static Transformation getByNumber(int number) {
        if (number < 0 || number >= values().length) {
            throw new IllegalArgumentException("Invalid transformation number: " + number);
        }
        return values()[number].getTransformation();
    }
}
