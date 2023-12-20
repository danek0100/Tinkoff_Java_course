package edu.project4.transformations;

import edu.project4.components.Point;

public class DiamondTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double theta = theta(point);

        double newX = Math.sin(theta) * Math.cos(radius);
        double newY = Math.cos(theta) * Math.sin(radius);

        return new Point(newX, newY);
    }
}
