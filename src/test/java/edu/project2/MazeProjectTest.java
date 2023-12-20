package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import edu.hw9.Task3DynamicDFSSolver;

public class MazeProjectTest {

    private final Cell[][] testGrid = {
        {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL), new Cell(0, 4, Cell.Type.WALL), new Cell(0, 5, Cell.Type.WALL)},
        {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE), new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.PASSAGE), new Cell(1, 4, Cell.Type.PASSAGE), new Cell(1, 5, Cell.Type.WALL)},
        {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.WALL), new Cell(2, 4, Cell.Type.PASSAGE), new Cell(2, 5, Cell.Type.WALL)},
        {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.PASSAGE), new Cell(3, 2, Cell.Type.PASSAGE), new Cell(3, 3, Cell.Type.PASSAGE), new Cell(3, 4, Cell.Type.PASSAGE), new Cell(3, 5, Cell.Type.WALL)},
        {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.PASSAGE), new Cell(4, 2, Cell.Type.WALL), new Cell(4, 3, Cell.Type.WALL), new Cell(4, 4, Cell.Type.WALL), new Cell(4, 5, Cell.Type.WALL)},
        {new Cell(5, 0, Cell.Type.WALL), new Cell(5, 1, Cell.Type.PASSAGE), new Cell(5, 2, Cell.Type.PASSAGE), new Cell(5, 3, Cell.Type.WALL), new Cell(5, 4, Cell.Type.PASSAGE), new Cell(5, 5, Cell.Type.WALL)},
        {new Cell(6, 0, Cell.Type.WALL), new Cell(6, 1, Cell.Type.WALL), new Cell(6, 2, Cell.Type.WALL), new Cell(6, 3, Cell.Type.WALL), new Cell(6, 4, Cell.Type.WALL), new Cell(6, 5, Cell.Type.WALL)}
    };

    private final List<Solver> solvers = List.of(new BFSSolver(), new DFSSolver(), new Task3DynamicDFSSolver());
    private final List<Generator> generators = List.of(new DFSMazeGenerator(), new KruskalMazeGenerator());

    @Test
    @DisplayName("Render maze")
    void testRenderMaze() {
        Maze maze = new Maze(testGrid);

        SimpleMazeRenderer renderer = new SimpleMazeRenderer();
        String renderedMaze = renderer.render(maze);

        String expectedOutput =
            "▉▉▉▉▉▉\n" +
            "▉    ▉\n" +
            "▉▉▉▉ ▉\n" +
            "▉    ▉\n" +
            "▉ ▉▉▉▉\n" +
            "▉  ▉ ▉\n" +
            "▉▉▉▉▉▉\n";

        assertThat(renderedMaze).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Render maze with solution")
    void testRenderMazeWithSolution() {
        Maze maze = new Maze(testGrid);

        SimpleMazeRenderer renderer = new SimpleMazeRenderer();

        List<Coordinate> solution = List.of(
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(1, 3),
            new Coordinate(1, 4),
            new Coordinate(2, 4),
            new Coordinate(3, 4),
            new Coordinate(3, 3),
            new Coordinate(3, 2),
            new Coordinate(3, 1),
            new Coordinate(4, 1),
            new Coordinate(5, 1),
            new Coordinate(5, 2)
        );

        String renderedMaze = renderer.render(maze, solution);

        String expectedOutput =
            "▉▉▉▉▉▉\n" +
            "▉....▉\n" +
            "▉▉▉▉.▉\n" +
            "▉....▉\n" +
            "▉.▉▉▉▉\n" +
            "▉..▉ ▉\n" +
            "▉▉▉▉▉▉\n";

        assertThat(renderedMaze).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Solvers finds path")
    void solversFindsPath() {
        Maze maze = new Maze(testGrid);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(5, 2);

        for (Solver solver : solvers)
        {
            List<Coordinate> path = solver.solve(maze, start, end);

            assertThat(path).isNotEmpty();
            assertThat(path.get(0)).isEqualTo(start);
            assertThat(path.get(path.size() - 1)).isEqualTo(end);
        }
    }

    @Test
    @DisplayName("Solvers does not find path when impossible")
    void solversDoesNotFindPathWhenImpossible() {
        Maze maze = new Maze(testGrid);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(5, 4);

        for (Solver solver : solvers)
        {
            List<Coordinate> path = solver.solve(maze, start, end);
            assertThat(path).isEmpty();
        }
    }

    @Test
    @DisplayName("Creating maze with negative dimensions throws exception")
    void testMazeCreationWithNegativeDimensions() {
        assertThatThrownBy(() -> new Maze(-1, -1, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must be positive");
    }

    @Test
    @DisplayName("Creating maze with zero dimensions throws exception")
    void testMazeCreationWithZeroDimensions() {
        assertThatThrownBy(() -> new Maze(0, 0, new Cell[0][0]))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must be positive");
    }

    @Test
    @DisplayName("Creating maze with null grid throws exception")
    void testMazeCreationWithNullGrid() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Maze(null))
            .withMessage("Grid cannot be null or empty.");
    }

    @Test
    @DisplayName("Creating maze with empty grid throws exception")
    void testMazeCreationWithEmptyGrid() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Maze(new Cell[0][]))
            .withMessage("Grid cannot be null or empty.");
    }

    @Test
    @DisplayName("Creating maze with irregular grid throws exception")
    void testMazeCreationWithIrregularGrid() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.PASSAGE)},
            {new Cell(1, 0, Cell.Type.WALL)} // Irregular grid
        };

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Maze(grid))
            .withMessage("All rows in the grid must be non-null and the same length.");
    }

    @Test
    @DisplayName("Creating maze with null cells in grid throws exception")
    void testMazeCreationWithNullCells() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), null},
            {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE)}
        };

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Maze(grid))
            .withMessage("Cells in the grid cannot be null.");
    }

    @Test
    @DisplayName("Cell constructor assigns correct values")
    void testCellConstructorAssignsValues() {
        int row = 5;
        int col = 10;
        Cell.Type type = Cell.Type.PASSAGE;

        Cell cell = new Cell(row, col, type);

        assertThat(cell.row).isEqualTo(row);
        assertThat(cell.col).isEqualTo(col);
        assertThat(cell.type).isEqualTo(type);
    }

    @Test
    @DisplayName("Cell Type enum has correct values")
    void testCellTypeEnumValues() {
        assertThat(Cell.Type.WALL).isNotNull();
        assertThat(Cell.Type.PASSAGE).isNotNull();
    }

    @Test
    @DisplayName("Solving with null maze throws exception")
    void solvingWithNullMazeThrowsException() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(1, 1);

        for (Solver solver : solvers) {
            assertThatThrownBy(() -> solver.solve(null, start, end))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("Solving with null start or end throws exception")
    void solvingWithNullStartOrEndThrowsException() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        Maze maze = new Maze(3, 3, cells);

        for (Solver solver : solvers) {
            assertThatThrownBy(() -> solver.solve(maze, null, new Coordinate(1, 1)))
                .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), null))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("Solving with start or end outside maze bounds throws exception")
    void solvingWithStartOrEndOutsideMazeBoundsThrowsException() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        Maze maze = new Maze(3, 3, cells);

        for (Solver solver : solvers) {
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(-1, 0), new Coordinate(1, 1)))
                .isInstanceOf(IndexOutOfBoundsException.class);

            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 3)))
                .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    @Test
    @DisplayName("Solving with start or end as wall throws exception")
    void solvingWithStartOrEndAsWallThrowsException() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.PASSAGE)},
            {new Cell(1, 0, Cell.Type.PASSAGE), new Cell(1, 1, Cell.Type.WALL)}
        };
        Maze maze = new Maze(2, 2, grid);

        for (Solver solver : solvers) {
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(0, 0), new Coordinate(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start and end positions must be passages");

            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 0), new Coordinate(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start and end positions must be passages");
        }
    }

    @Test
    void testMazeHasCorrectDimensions() {
        int height = 10;
        int width = 10;

        for (Generator generator : generators) {
            Maze maze = generator.generate(height, width);
            assertThat(maze.getHeight()).isEqualTo(height);
            assertThat(maze.getWidth()).isEqualTo(width);
        }
    }

    @Test
    void testMazeBoundaryWalls() {
        int height = 11;
        int width = 11;

        for (Generator generator : generators) {
            Maze maze = generator.generate(height, width);
            Cell[][] grid = maze.getGrid();

            // Check the top and bottom boundary
            for (int x = 0; x < width; x++) {
                assertThat(grid[0][x].type).isEqualTo(Cell.Type.WALL);
                assertThat(grid[height-1][x].type).isEqualTo(Cell.Type.WALL);
            }

            // Check the left and right boundary
            for (int y = 0; y < height; y++) {
                assertThat(grid[y][0].type).isEqualTo(Cell.Type.WALL);
                assertThat(grid[y][width - 1].type).isEqualTo(Cell.Type.WALL);
            }
        }
    }


    @Test
    @DisplayName("Generating maze with negative dimensions throws exception")
    void generatingMazeWithNegativeDimensionsThrowsException() {
        for (Generator generator : generators) {
            assertThatThrownBy(() -> generator.generate(-1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Dimensions must be positive");
        }
    }

    @Test
    @DisplayName("Generating maze with insufficient dimensions throws exception")
    void generatingMazeWithInsufficientDimensionsThrowsException() {
        for (Generator generator : generators) {
            assertThatThrownBy(() -> generator.generate(1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Dimensions must allow for a valid maze");
        }
    }
}
