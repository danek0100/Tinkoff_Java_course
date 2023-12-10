package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {

    default double radius(Point point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y());
    }

    default double radiusSquared(Point point) {
        return point.x() * point.x() + point.y() * point.y();
    }

    default double theta(Point point) {
        return Math.atan2(point.y(), point.x());
    }
}
