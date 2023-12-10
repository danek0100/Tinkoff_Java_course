package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.function.Function;

/**
 * An interface representing a mathematical transformation on a 2D point in a fractal.
 * Implementations of this interface define how a transformation operates on a given point.
 */
public interface Transformation extends Function<Point, Point> {

    /**
     * Calculates the radius (distance from the origin) of a given point.
     *
     * @param point The input point.
     * @return The radius of the point.
     */
    default double radius(Point point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y());
    }

    /**
     * Calculates the squared radius of a given point.
     *
     * @param point The input point.
     * @return The squared radius of the point.
     */
    default double radiusSquared(Point point) {
        return point.x() * point.x() + point.y() * point.y();
    }

    /**
     * Calculates the polar angle (theta) of a given point in radians.
     *
     * @param point The input point.
     * @return The polar angle (theta) of the point in radians.
     */
    default double theta(Point point) {
        return Math.atan2(point.y(), point.x());
    }
}
