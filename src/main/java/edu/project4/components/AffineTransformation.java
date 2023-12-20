package edu.project4.components;

/**
 * Represents an affine transformation with coefficients and color information.
 */
public record AffineTransformation(double a, double b, double c, double d, double e, double f,
                                   int red, int green, int blue) {
}
