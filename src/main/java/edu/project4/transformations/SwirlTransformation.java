package edu.project4.transformations;

import edu.project4.components.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radiusSquared = radiusSquared(point);

        double newX = point.x() * Math.sin(radiusSquared) - point.y() * Math.cos(radiusSquared);
        double newY = point.x() * Math.cos(radiusSquared) + point.y() * Math.sin(radiusSquared);

        return new Point(newX, newY);
    }
}
