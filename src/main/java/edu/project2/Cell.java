package edu.project2;

/**
 * Represents a single cell within a maze.
 * A cell can either be a wall or a passage, as defined by the Type enumeration.
 */
public class Cell {

    public enum Type { WALL, PASSAGE }

    public int row;
    public int col;
    public Type type;

    /**
     * Constructs a Cell with specified row, column, and type.
     *
     * @param row  The row number of the cell.
     * @param col  The column number of the cell.
     * @param type The type of the cell (wall or passage).
     */
    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }
}
