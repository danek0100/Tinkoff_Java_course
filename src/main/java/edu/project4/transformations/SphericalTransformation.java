package edu.project4.transformations;

import edu.project4.components.Point;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radiusSquared = radiusSquared(point);

        double newX = point.x() / radiusSquared;
        double newY = point.y() / radiusSquared;

        return new Point(newX, newY);
    }
}
