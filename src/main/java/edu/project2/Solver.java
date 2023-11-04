package edu.project2;

import java.util.List;

/**
 * The {@code Solver} interface defines the structure for maze-solving algorithms.
 * Implementations of this interface should provide the mechanism to find a path
 * from the start to the end within a maze.
 */
public interface Solver {

    /**
     * Solves the given maze, attempting to find a path from the start coordinate to the end coordinate.
     *
     * @param maze  The {@code Maze} to be solved.
     * @param start The starting {@code Coordinate} for the path.
     * @param end   The ending {@code Coordinate} for the path.
     * @return A list of {@code Coordinate} objects that represent the path from start to end in the maze.
     *         If no path is found, this method should return an empty list.
     */
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
