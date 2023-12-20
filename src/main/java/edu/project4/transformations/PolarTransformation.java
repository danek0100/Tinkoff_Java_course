package edu.project4.transformations;

import edu.project4.components.Point;

public class PolarTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double newX = theta(point) / Math.PI;
        double newY = radius(point) - 1;

        return new Point(newX, newY);
    }
}
