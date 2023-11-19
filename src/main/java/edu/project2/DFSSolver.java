package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Solver that uses Depth-First Search (DFS) algorithm to find a path through a maze.
 */
public class DFSSolver implements Solver {

    /**
     * Solves the maze using a depth-first search approach starting from the start coordinate and
     * aiming to reach the end coordinate.
     *
     * @param maze  the maze to be solved
     * @param start the starting coordinate for the path
     * @param end   the ending coordinate for the path
     * @return a list of coordinates representing the path from start to end or an empty list if no path is found
     * @throws IllegalArgumentException if the maze, start, or end are null or if the start or end are not passages
     * @throws IndexOutOfBoundsException if the start or end coordinates are outside the maze bounds
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {

        if (maze == null || start == null || end == null) {
            throw new IllegalArgumentException("Maze, start, and end cannot be null.");
        }

        if (!isValidCoordinate(maze, start) || !isValidCoordinate(maze, end)) {
            throw new IndexOutOfBoundsException("Start or end coordinate is outside the maze bounds.");
        }

        if (maze.getCell(start.row(), start.col()).type != Cell.Type.PASSAGE
            || maze.getCell(end.row(), end.col()).type != Cell.Type.PASSAGE) {
            throw new IllegalArgumentException("Start and end positions must be passages.");
        }

        Stack<Coordinate> stack = new Stack<>();
        List<Coordinate> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        stack.push(start);

        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();

            if (current.equals(end)) {
                path.add(current);
                return path;
            }

            visited[current.row()][current.col()] = true;
            path.add(current);

            List<Coordinate> neighbors = getNeighbors(maze, current);

            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    stack.push(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Retrieves the neighboring coordinates around a given coordinate that are within the maze bounds and are passages.
     *
     * @param maze  the maze containing the coordinates
     * @param coord the coordinate from which neighbors are found
     * @return a list of neighboring coordinates
     */
    private List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[0];
            int newCol = coord.col() + dir[1];

            if (newRow >= 0 && newRow < maze.getHeight() && newCol >= 0 && newCol < maze.getWidth()
                && maze.getCell(newRow, newCol).type == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }

    /**
     * Validates whether a coordinate is within the bounds of the maze.
     *
     * @param maze  the maze containing the coordinates
     * @param coord the coordinate to be validated
     * @return true if the coordinate is within the bounds of the maze, false otherwise
     */
    private boolean isValidCoordinate(Maze maze, Coordinate coord) {
        return coord.row() >= 0 && coord.row() < maze.getHeight()
            && coord.col() >= 0 && coord.col() < maze.getWidth();
    }
}
