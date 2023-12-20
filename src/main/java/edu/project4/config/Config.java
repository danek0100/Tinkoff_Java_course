package edu.project4.config;

import edu.project4.components.AffineTransformation;
import edu.project4.transformations.NonLinearTransformations;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ImageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Configuration class that holds various parameters and settings for fractal image generation.
 */
public class Config {
    private int height;
    private int width;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private int threadsCount;
    private int symmetry;
    private int samples;
    private int iterations;
    private int randomAffineTransformationsCount;
    private ImageFormat fileType;
    private boolean withCorrection;
    private AffineTransformation[] affineTransformations;
    private String directory;
    private String filename;
    private Transformation[] nonlinearTransformations;
    private double gamma;
    private int seed;

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

    public AffineTransformation[] getPresetAffineTransformations() {
        return affineTransformations;
    }

    public void setPresetAffineTransformations(AffineTransformation[] affineTransformations) {
        this.affineTransformations = affineTransformations;
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


    /**
     * Constructs a Config object with the specified parameters.
     *
     * @param height                      The height of the image.
     * @param width                       The width of the image.
     * @param minX                        The minimum x-coordinate.
     * @param maxX                        The maximum x-coordinate.
     * @param minY                        The minimum y-coordinate.
     * @param maxY                        The maximum y-coordinate.
     * @param threadsCount                The number of threads to use for image generation.
     * @param symmetry                    The symmetry factor for fractal generation.
     * @param samples                     The number of samples per pixel.
     * @param iterations                  The number of iterations for fractal generation.
     * @param randomAffineTransformationsCount The count of random affine transformations.
     * @param fileType                    The image file format (e.g., PNG, JPEG).
     * @param withCorrection              Whether to apply color correction.
     * @param affineTransformations        An array of preset affine transformations.
     * @param directory                   The directory to save the generated image.
     * @param filename                    The name of the generated image file.
     * @param nonlinearTransformations    An array of selected nonlinear transformations.
     * @param gamma                       The gamma correction factor for color.
     * @param seed                        The random seed for image generation.
     */
    @SuppressWarnings("ParameterNumber")
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
        AffineTransformation[] affineTransformations,
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
        this.affineTransformations = affineTransformations;
        this.directory = directory;
        this.filename = filename;
        this.nonlinearTransformations = nonlinearTransformations;
        this.gamma = gamma;
        this.seed = seed;
    }
}
