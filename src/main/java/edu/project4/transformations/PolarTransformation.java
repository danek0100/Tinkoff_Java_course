package edu.project4.transformations;

import edu.project4.components.Point;

public class PolarTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double newX = theta / Math.PI;
        double newY = r - 1;
        return new Point(newX, newY);
    }
}
