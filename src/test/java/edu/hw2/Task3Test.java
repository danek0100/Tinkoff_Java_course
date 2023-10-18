package edu.hw2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task3Test {

    private Task3.FaultyConnection faultyConnection;
    private Task3.DefaultConnectionManager defaultConnectionManager;

    @BeforeEach
    public void setUp() {
        faultyConnection = new Task3.FaultyConnection();
        defaultConnectionManager = new Task3.DefaultConnectionManager();
    }

    @AfterEach
    public void tearDown() {
        // Reset the probabilities after each test to ensure isolation between tests
        faultyConnection.setProbability(0.5);
        defaultConnectionManager.setProbability(0.5);
    }

    @Test
    @DisplayName("FaultyConnection should fail")
    public void faultyConnection_ShouldAlwaysFail_WithSetProbability() {
        faultyConnection.setProbability(0.0); // Always fail

        assertThatThrownBy(() -> faultyConnection.execute("any command"))
            .isInstanceOf(Task3.ConnectionException.class)
            .hasMessageContaining("Error executing command in FaultyConnection");
    }

    @Test
    @DisplayName("DefaultConnectionManager return FaultyConnection")
    public void defaultConnectionManager_ShouldAlwaysReturnFaultyConnection_WithSetProbability() {
        defaultConnectionManager.setProbability(0.0); // Always return faulty connection

        Task3.Connection connection = defaultConnectionManager.getConnection();
        assertThat(connection).isInstanceOf(Task3.FaultyConnection.class);
    }

    @Test
    @DisplayName("DefaultConnectionManager return StableConnection")
    public void defaultConnectionManager_ShouldAlwaysReturnStableConnection_WithSetProbability() {
        defaultConnectionManager.setProbability(1.0); // Always return stable connection

        Task3.Connection connection = defaultConnectionManager.getConnection();
        assertThat(connection).isInstanceOf(Task3.StableConnection.class);
    }

    @Test
    @DisplayName("PopularCommandExecutor can execute command with faulty connection")
    public void popularCommandExecutor_ShouldExecuteCommand_WithStableConnection() {
        Task3.PopularCommandExecutor executor = new Task3.PopularCommandExecutor(new Task3.FaultyConnectionManager(), 1);
        faultyConnection.setProbability(1.0); // Ensure the FaultyConnection always works

        assertThatCode(executor::updatePackages).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("PopularCommandExecutor should handle faulty connection")
    public void popularCommandExecutor_ShouldHandle_FaultyConnection() {
        Task3.PopularCommandExecutor executor = new Task3.PopularCommandExecutor(new Task3.FaultyConnectionManager(), 1);
        faultyConnection.setProbability(0.0); // Ensure the FaultyConnection always fails

        assertThatExceptionOfType(Task3.ConnectionException.class)
            .isThrownBy(executor::updatePackages)
            .withMessageContaining("Failed to execute the command after 1 attempts");
    }

}
