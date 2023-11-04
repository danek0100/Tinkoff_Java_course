package edu.project2;

/**
 * Interface for generating mazes of specified dimensions.
 */
public interface Generator {

    /**
     * Generates a maze with the given height and width.
     *
     * Implementations of this method should ensure that the generated maze has a solvable path
     * and meets the constraints of the specified dimensions. The maze should be bounded, with
     * the outer walls enclosing the passable area, and contain a start and end point that are
     * reachable from one another.
     *
     * @param height the height of the maze (number of rows)
     * @param width the width of the maze (number of columns)
     * @return a {@link Maze} object representing the generated maze
     * @throws IllegalArgumentException if the provided dimensions are not positive or not suitable
     *         for generating a valid maze structure
     */
    Maze generate(int height, int width) throws IllegalArgumentException;
}
