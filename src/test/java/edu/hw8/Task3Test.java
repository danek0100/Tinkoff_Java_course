package edu.hw8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.util.HashMap;
import java.util.Map;

public class Task3Test {

    private Map<String, String> toSolve;

    @BeforeEach
    void init() {
        toSolve = new HashMap<>();
        toSolve.put("900150983cd24fb0d6963f7d28e17f72", "a.s.ivanov"); // abc
        toSolve.put("b25b0651e4b6e887e5194135d3692631", "k.p.maslov"); // d2
        toSolve.put("0cc175b9c0f1b6a831c399e269772661", "d.a.sobolev"); // a
    }

    @Test
    void passwordSingleSolverTest() {
        Task3SingleSolver solver = new Task3SingleSolver();

        try {
            Map<String, String> result = solver.solve(toSolve, 3);
            assertThat(result).isNotNull();
            assertThat(result)
                .containsEntry("a.s.ivanov", "abc")
                .containsEntry("k.p.maslov", "d2")
                .containsEntry("d.a.sobolev", "a");
        } catch (Exception ex) {
            failBecauseExceptionWasNotThrown(Exception.class);
        }
    }

    @Test
    void passwordParallelSolverTest() {
        Task3ParallelSolver solver = new Task3ParallelSolver();

        try {
            Map<String, String> result = solver.solve(toSolve, 3);
            assertThat(result).isNotNull();
            assertThat(result)
                .containsEntry("a.s.ivanov", "abc")
                .containsEntry("k.p.maslov", "d2")
                .containsEntry("d.a.sobolev", "a");
        } catch (Exception ex) {
            failBecauseExceptionWasNotThrown(Exception.class);
        }
    }
}
