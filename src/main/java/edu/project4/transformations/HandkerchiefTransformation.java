package edu.project4.transformations;

import edu.project4.components.Point;

public class HandkerchiefTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double theta = theta(point);

        double newX = radius * Math.sin(theta + radius);
        double newY = radius * Math.cos(theta - radius);

        return new Point(newX, newY);
    }
}
