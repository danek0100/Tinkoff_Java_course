package edu.hw2;

/**
 * This class represents a mathematical expression evaluator.
 */
public class Task2 {

    static class Rectangle {
        protected final int width;
        protected final int height;

        public Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Rectangle setWidth(int width) {
            return new Rectangle(width, this.height);
        }

        public Rectangle setHeight(int height) {
            return new Rectangle(this.width, height);
        }

        public double area() {
            return width * height;
        }

        @Override
        public String toString() {
            return "Rectangle [width=" + width + ", height=" + height + "]";
        }
    }

    static final class Square extends Rectangle {
        public Square(int side) {
            super(side, side);
        }

        @Override
        public Square setWidth(int side) {
            return new Square(side);
        }

        @Override
        public Square setHeight(int side) {
            return new Square(side);
        }
    }
}
