package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class implements the BFS algorithm to solve mazes.
 * BFS (Breadth-First Search) is a graph traversal algorithm
 * that starts from a source node and explores all of the neighbor nodes
 * at the present depth prior to moving on to nodes at the next depth level.
 */
public class BFSSolver implements Solver {

    /**
     * Solves the given maze using the BFS algorithm.
     *
     * @param maze  The maze to solve.
     * @param start The starting coordinate in the maze.
     * @param end   The ending coordinate in the maze.
     * @return A list of Coordinates representing the path from start to end,
     * or an empty list if no path is found.
     * @throws IllegalArgumentException if the maze or any of the coordinates is null,
     *                                  or if the start or end is not a passage.
     * @throws IndexOutOfBoundsException if the start or end is outside the maze bounds.
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

        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        queue.offer(start);
        visited[start.row()][start.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                return buildPath(prev, end);
            }

            List<Coordinate> neighbors = getNeighbors(maze, current);
            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    queue.offer(neighbor);
                    visited[neighbor.row()][neighbor.col()] = true;
                    prev.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Retrieves the neighboring cells of a given coordinate in the maze that are passages.
     *
     * @param maze  The maze where the coordinate exists.
     * @param coord The coordinate whose neighbors are to be retrieved.
     * @return A list of neighboring coordinates.
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
     * Reconstructs the path from the end coordinate to the start by backtracking
     * through the 'prev' map which holds the breadcrumbs of our BFS traversal.
     *
     * @param prev A map containing each coordinate and the coordinate from which it was reached.
     * @param end  The end coordinate from where to start the backtracking.
     * @return A list of Coordinates representing the path from start to end.
     */
    private List<Coordinate> buildPath(Map<Coordinate, Coordinate> prev, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate at = end;

        while (at != null) {
            path.add(at);
            at = prev.get(at);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Checks if a coordinate is valid within the maze bounds.
     *
     * @param maze  The maze to check within.
     * @param coord The coordinate to check.
     * @return true if the coordinate is within the bounds of the maze, false otherwise.
     */
    private boolean isValidCoordinate(Maze maze, Coordinate coord) {
        return coord.row() >= 0 && coord.row() < maze.getHeight()
            && coord.col() >= 0 && coord.col() < maze.getWidth();
    }
}
