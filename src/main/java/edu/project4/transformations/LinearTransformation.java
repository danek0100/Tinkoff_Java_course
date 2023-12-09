package edu.project4.transformations;

import edu.project4.components.Point;

public class LinearTransformation implements Transformation {

    private final double scaleX;
    private final double scaleY;
    private final double translateX;
    private final double translateY;

    public LinearTransformation(double scaleX, double scaleY, double translateX, double translateY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    @Override
    public Point apply(Point point) {
        double x = point.x() * scaleX + translateX;
        double y = point.y() * scaleY + translateY;
        return new Point(x, y);
    }
}
