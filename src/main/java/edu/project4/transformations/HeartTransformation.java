package edu.project4.transformations;

import edu.project4.components.Point;

public class HeartTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double theta = theta(point);

        double newX = radius * Math.sin(radius * theta);
        double newY = radius * -Math.cos(radius * theta);

        return new Point(newX, newY);
    }
}
