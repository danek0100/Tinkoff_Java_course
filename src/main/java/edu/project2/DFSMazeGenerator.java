package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Generates a maze using the Depth-First Search algorithm.
 * This class provides a method to generate a maze with a specified height and width.
 * The generated maze has a single solution path.
 */
public class DFSMazeGenerator implements Generator {

    private final static int ZERO_OFFSET = 0;
    private final static int DOUBLE_OFFSET = 2;
    private final static int OFFSETS = 4;
    private final static int MIN_SIDE_SIZE = 3;


    /**
     * Generates a maze with the given dimensions.
     *
     * @param height the height of the maze, must be a positive odd number greater than 2
     * @param width  the width of the maze, must be a positive odd number greater than 2
     * @return a {@link Maze} object representing the generated maze
     * @throws IllegalArgumentException if the provided dimensions are not positive or small to generate a valid maze
     */
    @Override
    public Maze generate(int height, int width) throws IllegalArgumentException {

        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        if (height < MIN_SIDE_SIZE || width < MIN_SIDE_SIZE) {
            throw new IllegalArgumentException("Dimensions must allow for a valid maze");
        }

        Cell[][] grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        Stack<Cell> stack = new Stack<>();
        Cell start = grid[1][1];
        start.type = Cell.Type.PASSAGE;
        stack.push(start);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            List<Cell> neighbors = getUnvisitedNeighbors(current, grid);
            if (!neighbors.isEmpty()) {
                Cell next = neighbors.get((int) (Math.random() * neighbors.size()));
                removeWall(current, next, grid);
                stack.push(next);
            } else {
                stack.pop();
            }
        }

        return new Maze(grid);
    }

    /**
     * Gets the unvisited neighboring cells of a given cell within the maze grid.
     *
     * @param cell the cell for which to find unvisited neighbors
     * @param grid the grid of cells representing the maze
     * @return a list of unvisited neighboring cells
     */
    private List<Cell> getUnvisitedNeighbors(Cell cell, Cell[][] grid) {
        List<Cell> neighbors = new ArrayList<>();
        int[] rowOffsets = {-DOUBLE_OFFSET, DOUBLE_OFFSET, ZERO_OFFSET, ZERO_OFFSET};
        int[] colOffsets = {ZERO_OFFSET, ZERO_OFFSET, -DOUBLE_OFFSET, DOUBLE_OFFSET};

        for (int i = 0; i < OFFSETS; i++) {
            int newRow = cell.row + rowOffsets[i];
            int newCol = cell.col + colOffsets[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length
                && grid[newRow][newCol].type == Cell.Type.WALL) {
                neighbors.add(grid[newRow][newCol]);
            }
        }
        Collections.shuffle(neighbors);
        return neighbors;
    }

    /**
     * Removes the wall between two adjacent cells.
     *
     * @param current the current cell from which the wall is being removed
     * @param next    the next cell to which the passage is being opened
     * @param grid    the grid of cells representing the maze
     */
    private void removeWall(Cell current, Cell next, Cell[][] grid) {
        int dRow = (current.row + next.row) / 2;
        int dCol = (current.col + next.col) / 2;
        grid[dRow][dCol].type = Cell.Type.PASSAGE;
        next.type = Cell.Type.PASSAGE;
    }
}
