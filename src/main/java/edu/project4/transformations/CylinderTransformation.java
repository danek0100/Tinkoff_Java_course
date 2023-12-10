package edu.project4.transformations;

import edu.project4.components.Point;;

public class CylinderTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.sin(point.x());
        double newY = point.y();

        return new Point(newX, newY);
    }
}
