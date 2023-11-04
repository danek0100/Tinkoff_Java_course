package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A maze generator that uses Kruskal's algorithm to produce a maze.
 */
public class KruskalMazeGenerator implements Generator {
    private final Random random = new Random();
    private final static int MIN_SIDE_SIZE = 3;

    /**
     * Generates a maze using Kruskal's algorithm.
     *
     * This algorithm treats the maze as a graph and adds edges (walls) to a set. It randomly
     * removes walls to connect disjoint sets of cells while avoiding the creation of cycles,
     * thus ensuring there's a single unique path between any two cells in the maze.
     *
     * @param height the vertical size of the maze (number of cells tall).
     * @param width  the horizontal size of the maze (number of cells wide).
     * @return a {@code Maze} object representing the generated maze.
     * @throws IllegalArgumentException if the height or width is less than {@code MIN_SIDE_SIZE},
     *                                  which is required to generate a valid maze.
     */
    @Override
    public Maze generate(int height, int width) throws IllegalArgumentException {

        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        if (height < MIN_SIDE_SIZE || width < MIN_SIDE_SIZE) {
            throw new IllegalArgumentException("Dimensions must allow for a valid maze");
        }

        UnionFind unionFind = new UnionFind(width, height);
        Cell[][] cells = new Cell[height][width];
        List<Wall> walls = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            cells[0][x] = new Cell(0, x, Cell.Type.WALL);
            cells[height - 1][x] = new Cell(height - 1, x, Cell.Type.WALL);
        }

        for (int y = 0; y < height; y++) {
            cells[y][0] = new Cell(y, 0, Cell.Type.WALL);
            cells[y][width - 1] = new Cell(y, width - 1, Cell.Type.WALL);
        }


        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                cells[y][x] = new Cell(y, x, Cell.Type.WALL);

                if (y == height - 2 && x == width - 2) {
                    cells[y][x].type = Cell.Type.PASSAGE;
                }

                if (x < width - 2) {
                    walls.add(new Wall(new Coordinate(y, x), new Coordinate(y, x + 1)));
                }
                if (y < height - 2) {
                    walls.add(new Wall(new Coordinate(y, x), new Coordinate(y + 1, x)));
                }
            }
        }

        Collections.shuffle(walls, random);

        for (Wall wall : walls) {
            Coordinate cell1 = wall.cell1;
            Coordinate cell2 = wall.cell2;

            if (!unionFind.find(cell1).equals(unionFind.find(cell2))) {
                unionFind.union(cell1, cell2);

                if (cell1.row() == cell2.row()) {
                    cells[cell1.row()][Math.min(cell1.col(), cell2.col())].type = Cell.Type.PASSAGE;
                } else {
                    cells[Math.min(cell1.row(), cell2.row())][cell1.col()].type = Cell.Type.PASSAGE;
                }
            }
        }

        return new Maze(cells);
    }

    /**
     * Represents a wall between two cells in the maze.
     */
    private static class Wall {
        public Coordinate cell1;
        public Coordinate cell2;

        /**
         * Creates a wall between two specified cells.
         *
         * @param cell1 the first cell connected by the wall.
         * @param cell2 the second cell connected by the wall.
         */
        Wall(Coordinate cell1, Coordinate cell2) {
            this.cell1 = cell1;
            this.cell2 = cell2;
        }
    }

    /**
     * A union-find data structure to manage disjoint sets of cells during maze generation.
     */
    private static class UnionFind {
        private final Map<Coordinate, Coordinate> parent = new HashMap<>();

        /**
         * Initializes the union-find structure with each cell in its own set.
         *
         * @param width  the width of the maze (number of cells horizontally).
         * @param height the height of the maze (number of cells vertically).
         */
        UnionFind(int width, int height) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Coordinate coordinate = new Coordinate(y, x);
                    parent.put(coordinate, coordinate);
                }
            }
        }

        /**
         * Finds the representative of the set that the specified cell is in.
         *
         * @param node the cell whose set representative is to be found.
         * @return the representative of the set containing the cell.
         */
        public Coordinate find(Coordinate node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node)));
            }
            return parent.get(node);
        }


        /**
         * Merges the sets containing the two specified cells.
         *
         * @param node1 the first cell whose set is to be merged.
         * @param node2 the second cell whose set is to be merged.
         */
        public void union(Coordinate node1, Coordinate node2) {
            Coordinate root1 = find(node1);
            Coordinate root2 = find(node2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2);
            }
        }
    }
}
