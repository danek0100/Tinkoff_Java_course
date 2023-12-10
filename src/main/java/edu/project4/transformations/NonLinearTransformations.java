package edu.project4.transformations;

import java.util.Random;

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
    SWIRL(new SwirlTransformation());

    private final Transformation transformation;

    NonLinearTransformations(Transformation transformation) {
        this.transformation = transformation;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public static Transformation getRandomTransformation() {
        return values()[new Random().nextInt(values().length)].getTransformation();
    }

    public static Transformation getByNumber(int number) {
        if (number < 0 || number >= values().length) {
            throw new IllegalArgumentException("Invalid transformation number: " + number);
        }
        return values()[number].getTransformation();
    }
}

