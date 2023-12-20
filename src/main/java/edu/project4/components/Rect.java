package edu.project4.components;

/**
 * Represents a rectangular region defined by its position and size.
 */
public record Rect(double x, double y, double width, double height) {

    /**
     * Checks whether the given point is contained within this rectangle.
     *
     * @param p The point to check for containment.
     * @return True if the point is inside the rectangle, false otherwise.
     */
    public boolean contains(Point p) {
        return p.x() >= this.x
            && p.x() <= this.x + this.width
            && p.y() >= this.y
            && p.y() <= this.y + this.height;
    }
}
