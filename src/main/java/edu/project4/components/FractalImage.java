package edu.project4.components;

public record FractalImage(Pixel[] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(new Color(0, 0, 0), 0);
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (contains(x, y)) {
            return data[y * width + x];
        }
        return null;
    }

    public void updatePixel(int x, int y, Pixel newPixel) {
        if (contains(x, y)) {
            data[y * width + x] = newPixel;
        }
    }
}
