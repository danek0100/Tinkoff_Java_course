package edu.project4.transformations;

import edu.project4.components.Point;

public class DiskTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double theta = theta(point);

        double newX = theta * Math.sin(Math.PI * radius) / Math.PI;
        double newY = theta * Math.cos(Math.PI * radius) / Math.PI;
        return new Point(newX, newY);
    }
}

