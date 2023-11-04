package edu.project2;

import java.util.List;

/**
 * The {@code SimpleMazeRenderer} class implements the {@code Renderer} interface to
 * provide a simple text-based rendering of a maze.
 */
public class SimpleMazeRenderer implements Renderer {

    /**
     * Renders the maze as a {@code String} without a path.
     *
     * @param maze The {@code Maze} to be rendered.
     * @return A {@code String} representation of the maze without any path.
     */
    @Override
    public String render(Maze maze) {
        return renderMaze(maze, null);
    }

    /**
     * Renders the maze with a highlighted path as a {@code String}.
     *
     * @param maze The {@code Maze} to be rendered.
     * @param path A list of {@code Coordinate} objects representing the path through the maze.
     * @return A {@code String} representation of the maze with the path highlighted.
     */
    @Override
    public String render(Maze maze, List<Coordinate> path) {
        return renderMaze(maze, path);
    }

    /**
     * Helper method to render the maze with optional path highlighting.
     *
     * @param maze The {@code Maze} to be rendered.
     * @param path A list of {@code Coordinate} objects representing the path through the maze, can be {@code null}.
     * @return A {@code String} representation of the maze with optional path highlighting.
     */
    private String renderMaze(Maze maze, List<Coordinate> path) {
        StringBuilder builder = new StringBuilder();
        Cell[][] grid = maze.getGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (path != null && path.contains(new Coordinate(row, col))) {
                    builder.append('.');
                } else {
                    builder.append(grid[row][col].type == Cell.Type.WALL ? '▉' : ' ');
                }
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
