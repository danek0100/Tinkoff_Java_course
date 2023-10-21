package edu.hw2;

/**
 * Contains classes representing a Rectangle and a Square, demonstrating the
 * principle of encapsulation without relying on inheritance.
 */
public class Task2 {

    /**
     * Represents a rectangle with defined width and height.
     */
    static class Rectangle {
        protected final int width;
        protected final int height;

        /**
         * Constructs a rectangle with the given width and height.
         *
         * @param width  the width of the rectangle
         * @param height the height of the rectangle
         */
        Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Returns a new rectangle with updated width, preserving the original height.
         *
         * @param width the new width for the rectangle
         * @return a new rectangle with updated width
         */
        public Rectangle setWidth(int width) {
            return new Rectangle(width, this.height);
        }

        /**
         * Returns a new rectangle with updated height, preserving the original width.
         *
         * @param height the new height for the rectangle
         * @return a new rectangle with updated height
         */
        public Rectangle setHeight(int height) {
            return new Rectangle(this.width, height);
        }

        /**
         * Calculates and returns the area of the rectangle.
         *
         * @return the area of the rectangle
         */
        public double area() {
            return width * height;
        }

        @Override
        public String toString() {
            return "Rectangle [width=" + width + ", height=" + height + "]";
        }
    }

    /**
     * Represents a square, which is a special case of a rectangle where width
     * and height are equal.
     */
    static final class Square extends Rectangle {
        /**
         * Constructs a square with the given side length.
         *
         * @param side the side length of the square
         */
        Square(int side) {
            super(side, side);
        }

        /**
         * Returns a new square with updated side length.
         *
         * @param side the new side length for the square
         * @return a new square with updated side length
         */
        @Override
        public Square setWidth(int side) {
            return new Square(side);
        }

        /**
         * Returns a new square with updated side length. This method is equivalent
         * to {@link #setWidth(int)} since a square's width and height are always equal.
         *
         * @param side the new side length for the square
         * @return a new square with updated side length
         */
        @Override
        public Square setHeight(int side) {
            return new Square(side);
        }
    }
}
