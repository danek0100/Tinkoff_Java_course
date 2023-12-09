package edu.project4.transformations;

import edu.project4.components.Point;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double rSquared = point.x() * point.x() + point.y() * point.y();
        double newX = point.x() / rSquared;
        double newY = point.y() / rSquared;
        return new Point(newX, newY);
    }
}
