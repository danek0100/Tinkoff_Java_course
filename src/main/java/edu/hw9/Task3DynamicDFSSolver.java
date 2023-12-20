package edu.hw9;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * A recursive task implementation for solving a maze using depth-first search (DFS) in a multi-threaded manner.
 * This class extends RecursiveTask and uses a ForkJoinPool to efficiently execute DFS in parallel.
 * It implements the Solver interface, allowing it to be used as a strategy for maze solving.
 */
public class Task3DynamicDFSSolver extends RecursiveTask<List<Coordinate>> implements Solver {

    private Maze maze;
    private Coordinate current;
    private Coordinate end;
    private boolean[][] visited;

    /**
     * Default constructor for the initial call of DFS solver.
     */
    public Task3DynamicDFSSolver() {
    }

    /**
     * Constructor for recursive DFS calls. Initializes the solver with a maze,
     * the current coordinate, the target coordinate, and the visited state.
     *
     * @param maze     The maze to be solved.
     * @param current  The current coordinate in the maze.
     * @param end      The target coordinate in the maze.
     * @param visited  A 2D array representing visited cells in the maze.
     */
    private Task3DynamicDFSSolver(Maze maze, Coordinate current, Coordinate end, boolean[][] visited) {
        this.maze = maze;
        this.current = current;
        this.end = end;
        this.visited = visited;
    }

    /**
     * Initializes the solver with a maze and start/end coordinates. To be called before solving the maze.
     *
     * @param maze  The maze to be solved.
     * @param start The starting coordinate in the maze.
     * @param end   The ending (target) coordinate in the maze.
     */
    private void init(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        this.current = start;
        this.end = end;
        this.visited = new boolean[maze.getHeight()][maze.getWidth()];
    }

    /**
     * Executes the DFS task for finding a path in the maze. The method recursively searches through the maze,
     * forking new tasks for each unvisited neighbor of the current coordinate. If the end coordinate is reached,
     * the path to that point is returned.
     *
     * @return A list of coordinates representing the path from the current coordinate to the end coordinate,
     *         or an empty list if no such path exists.
     */
    @Override
    protected List<Coordinate> compute() {
        if (!isValidCoordinate(maze, current) || visited[current.row()][current.col()]) {
            return Collections.emptyList();
        }

        if (current.equals(end)) {
            List<Coordinate> path = new ArrayList<>();
            path.add(end);
            return path;
        }

        visited[current.row()][current.col()] = true;
        List<Coordinate> neighbors = getNeighbors(maze, current);
        List<Task3DynamicDFSSolver> tasks = new ArrayList<>();

        for (Coordinate neighbor : neighbors) {
            if (!visited[neighbor.row()][neighbor.col()]) {
                boolean[][] newVisited = cloneVisited();
                Task3DynamicDFSSolver task = new Task3DynamicDFSSolver(maze, neighbor, end, newVisited);
                task.fork();
                tasks.add(task);
            }
        }

        for (Task3DynamicDFSSolver task : tasks) {
            List<Coordinate> path = task.join();
            if (!path.isEmpty()) {
                path.add(0, current);
                return path;
            }
        }

        return Collections.emptyList();
    }

    /**
     * Creates a deep copy of the visited array.
     *
     * @return A new 2D array representing a copy of the visited cells in the maze.
     */
    private boolean[][] cloneVisited() {
        boolean[][] newVisited = new boolean[maze.getHeight()][maze.getWidth()];
        for (int i = 0; i < visited.length; i++) {
            System.arraycopy(visited[i], 0, newVisited[i], 0, visited[i].length);
        }
        return newVisited;
    }

    /**
     * Helper method to get the neighbors of a given coordinate in the maze.
     *
     * @param maze  The maze in which the search is being conducted.
     * @param coord The coordinate whose neighbors are to be found.
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
     * Helper method to check if a given coordinate is valid within the maze boundaries.
     *
     * @param maze  The maze to be checked against.
     * @param coord The coordinate to be validated.
     * @return True if the coordinate is within the maze boundaries, false otherwise.
     */
    private boolean isValidCoordinate(Maze maze, Coordinate coord) {
        return coord.row() >= 0 && coord.row() < maze.getHeight()
            && coord.col() >= 0 && coord.col() < maze.getWidth();
    }

    /**
     * Solves the given maze from the start to the end coordinate. Validates the inputs and utilizes
     * a ForkJoinPool for parallel DFS execution.
     *
     * @param maze  The maze to be solved.
     * @param start The starting coordinate in the maze.
     * @param end   The ending (target) coordinate in the maze.
     * @return A list of coordinates representing the path from start to end, or an empty list if no path is found.
     * @throws IllegalArgumentException If maze, start, or end is null, or if start/end are not passages.
     * @throws IndexOutOfBoundsException If start or end coordinates are outside the maze boundaries.
     */
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

        try (ForkJoinPool pool = new ForkJoinPool()) {
            Task3DynamicDFSSolver task = new Task3DynamicDFSSolver();
            task.init(maze, start, end);
            return pool.invoke(task);
        }
    }
}
