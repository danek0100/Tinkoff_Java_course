package edu.project4.transformations;

import edu.project4.components.Point;

public class HyperbolicTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double theta = theta(point);

        double newX = Math.sin(theta) / radius;
        double newY = radius * Math.cos(theta);

        return new Point(newX, newY);
    }
}
