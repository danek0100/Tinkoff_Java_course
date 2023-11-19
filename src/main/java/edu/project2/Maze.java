package edu.project2;

/**
 * Represents a maze with a specified height and width.
 */
public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    /**
     * Constructs a maze with the given dimensions and grid of cells.
     *
     * @param height the height of the maze (number of cell rows).
     * @param width  the width of the maze (number of cell columns).
     * @param grid   a 2D array of {@link Cell} objects representing the maze layout.
     * @throws IllegalArgumentException if height or width are non-positive, if the grid is null,
     *                                  if the dimensions of the grid do not match the given height and width,
     *                                  or if any row or cell in the grid is null.
     */
    public Maze(int height, int width, Cell[][] grid) throws IllegalArgumentException {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("The dimensions of the maze must be positive.");
        }
        if (grid == null) {
            throw new IllegalArgumentException("The grid cannot be null.");
        }
        if (grid.length != height || grid[0].length != width) {
            throw new IllegalArgumentException("The dimensions of the grid do not match the given height and width.");
        }
        for (Cell[] row : grid) {
            if (row == null || row.length != width) {
                throw new IllegalArgumentException("Rows of the grid cannot be null and must have the same width.");
            }
            for (Cell cell : row) {
                if (cell == null) {
                    throw new IllegalArgumentException("Cells of the grid cannot be null.");
                }
            }
        }

        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    /**
     * Constructs a maze from an existing grid of cells.
     * The height and width are inferred from the grid dimensions.
     *
     * @param grid a 2D array of {@link Cell} objects representing the maze layout.
     * @throws IllegalArgumentException if the grid is null or empty, if any row in the grid is null or of inconsistent
     *                                  length, or if any cell in the grid is null.
     */
    public Maze(Cell[][] grid) throws IllegalArgumentException {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty.");
        }
        for (Cell[] row : grid) {
            if (row == null || row.length != grid[0].length) {
                throw new IllegalArgumentException("All rows in the grid must be non-null and the same length.");
            }
            for (Cell cell : row) {
                if (cell == null) {
                    throw new IllegalArgumentException("Cells in the grid cannot be null.");
                }
            }
        }
        this.height = grid.length;
        this.width = grid[0].length;
        this.grid = grid;
    }

    /**
     * Retrieves the height of the maze.
     *
     * @return the number of cell rows in the maze.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the height of the maze.
     *
     * @return the number of cell rows in the maze.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the maze.
     *
     * @return the number of cell rows in the maze.
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Retrieves a specific cell from the maze.
     *
     * @param row the row index of the cell to retrieve.
     * @param col the column index of the cell to retrieve.
     * @return the {@link Cell} at the specified row and column.
     * @throws IndexOutOfBoundsException if the specified row or column are out of the grid's bounds.
     */
    public Cell getCell(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row > width || col < 0 || col > height) {
            throw new IndexOutOfBoundsException("Cell coordinates are out of bounds.");
        }
        return grid[row][col];
    }
}
