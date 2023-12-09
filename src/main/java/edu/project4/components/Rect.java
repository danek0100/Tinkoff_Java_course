package edu.project4.components;

public record Rect(double x, double y, double width, double height) {

    public boolean contains(Point p) {
        return p.x() >= this.x
            && p.x() <= this.x + this.width
            && p.y() >= this.y
            && p.y() <= this.y + this.height;
    }
}
