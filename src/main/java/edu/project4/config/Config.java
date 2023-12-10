package edu.project4.config;

import edu.project4.ImageFormat;
import edu.project4.transformations.Transformation;
import edu.project4.transformations.NonLinearTransformations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("checkstyle:MagicNumber")
public class Config {
    private int height = 1080;
    private int width = 1920;
    private double minX = -1.77;
    private double maxX = 1.77;
    private double minY = -1;
    private double maxY = 1;
    private int threadsCount = 1;
    private int symmetry = 1;
    private int samples = 30;
    private int iterations = 100_000;
    private int randomAffineTransformationsCount = 5;
    private ImageFormat fileType = ImageFormat.BMP;
    private boolean withCorrection = true;
    private PresetAffineTransformation[] presetAffineTransformations;
    private String directory = "./";
    private String filename = LocalDateTime.now().toString();
    private Transformation[] nonlinearTransformations;
    private double gamma = 2.2;
    private int seed = 42;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public void setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    public int getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(int symmetry) {
        this.symmetry = symmetry;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getRandomAffineTransformationsCount() {
        return randomAffineTransformationsCount;
    }

    public void setRandomAffineTransformationsCount(int randomAffineTransformationsCount) {
        this.randomAffineTransformationsCount = randomAffineTransformationsCount;
    }

    public ImageFormat getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = ImageFormat.valueOf(fileType);
    }

    public boolean isWithCorrection() {
        return withCorrection;
    }

    public void setWithCorrection(boolean withCorrection) {
        this.withCorrection = withCorrection;
    }

    public PresetAffineTransformation[] getPresetAffineTransformations() {
        return presetAffineTransformations;
    }

    public void setPresetAffineTransformations(PresetAffineTransformation[] presetAffineTransformations) {
        this.presetAffineTransformations = presetAffineTransformations;
    }

    public Transformation[] getNonlinearTransformations() {
        if (nonlinearTransformations == null) {
            this.nonlinearTransformations = Arrays.stream(NonLinearTransformations.values())
                .map(NonLinearTransformations::getTransformation)
                .toList().toArray(new Transformation[]{});
        }

        return nonlinearTransformations;
    }

    public void setNonlinearTransformations(String[] nonlinearTransformations) {
        List<Transformation> transformations = new ArrayList<>();

        for (String transformationName: nonlinearTransformations) {
            transformations.add(NonLinearTransformations.valueOf(transformationName).getTransformation());
        }

        this.nonlinearTransformations = transformations.toArray(new Transformation[]{});
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public Config(
        int height,
        int width,
        double minX,
        double maxX,
        double minY,
        double maxY,
        int threadsCount,
        int symmetry,
        int samples,
        int iterations,
        int randomAffineTransformationsCount,
        ImageFormat fileType,
        boolean withCorrection,
        PresetAffineTransformation[] presetAffineTransformations,
        String directory,
        String filename,
        Transformation[] nonlinearTransformations,
        double gamma,
        int seed
    ) {
        this.height = height;
        this.width = width;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.threadsCount = threadsCount;
        this.symmetry = symmetry;
        this.samples = samples;
        this.iterations = iterations;
        this.randomAffineTransformationsCount = randomAffineTransformationsCount;
        this.fileType = fileType;
        this.withCorrection = withCorrection;
        this.presetAffineTransformations = presetAffineTransformations;
        this.directory = directory;
        this.filename = filename;
        this.nonlinearTransformations = nonlinearTransformations;
        this.gamma = gamma;
        this.seed = seed;
    }
}
