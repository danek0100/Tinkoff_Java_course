package edu.project4.transformations;

import edu.project4.components.Point;

public class FisheyeTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = radius(point);

        double newX = 2 * point.y() / (radius + 1);
        double newY = 2 * point.x() / (radius + 1);

        return new Point(newX, newY);
    }
}
