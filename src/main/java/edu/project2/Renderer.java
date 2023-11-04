package edu.project2;

import java.util.List;

/**
 * The {@code Renderer} interface defines methods for rendering a maze and a path within it.
 */
public interface Renderer {

    /**
     * Renders the maze as a {@code String}.
     *
     * @param maze The {@code Maze} to be rendered.
     * @return A {@code String} representation of the maze.
     */
    String render(Maze maze);

    /**
     * Renders the maze with a highlighted path as a {@code String}.
     *
     * @param maze The {@code Maze} to be rendered.
     * @param path A list of {@code Coordinate} objects representing the path through the maze.
     * @return A {@code String} representation of the maze with the path highlighted.
     */
    String render(Maze maze, List<Coordinate> path);
}
