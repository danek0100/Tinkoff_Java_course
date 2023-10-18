package edu.hw2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides functionality for executing popular commands on a remote server.
 */
public class Task3 {

    // Constants for the probabilities
    private static final double DEFAULT_PROBABILITY = 0.5;

    // Logger instance
    private static final Logger LOGGER = LogManager.getLogger(Task3.class);


    /**
     * Represents a connection with the ability to execute commands.
     */
    public interface Connection extends AutoCloseable {
        /**
         * Executes the given command.
         *
         * @param command the command to be executed
         */
        void execute(String command);
    }

    /**
     * Provides connections for command execution.
     */
    public interface ConnectionManager {
        /**
         * Retrieves a new connection.
         *
         * @return a connection
         */
        Connection getConnection();
    }

    /**
     * Exception thrown when a connection issue occurs.
     */
    public static class ConnectionException extends RuntimeException {

        public ConnectionException(String message) {
            super(message);
        }

        public ConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Executor for popular commands with retry capability.
     */
    public static final class PopularCommandExecutor {
        private final ConnectionManager manager;
        private final int maxAttempts;

        public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
            this.manager = manager;
            this.maxAttempts = maxAttempts;
        }

        /**
         * Updates packages on the server.
         */
        public void updatePackages() {
            tryExecute("apt update && apt upgrade -y");
        }

        private void tryExecute(String command) {
            int attempts = 0;
            while (attempts < maxAttempts) {
                try (Connection connection = manager.getConnection()) {
                    connection.execute(command);
                    return;
                } catch (Exception e) {
                    attempts++;
                    if (attempts >= maxAttempts) {
                        throw new ConnectionException("Failed to execute the command after "
                            + maxAttempts + " attempts", e);
                    }
                }
            }
        }
    }

    /**
     * A stable connection that always succeeds.
     */
    public static class StableConnection implements Connection {
        @Override
        public void execute(String command) {
            LOGGER.info("Executing command in StableConnection: {}", command);
        }

        @Override
        public void close() {
            LOGGER.info("Closing StableConnection");
        }
    }

    /**
     * A connection that may throw exceptions during execution.
     */
    public static class FaultyConnection implements Connection {

        private static double successProbability = DEFAULT_PROBABILITY;

        void setProbability(double newProbability) {
            successProbability = newProbability;
        }

        @Override
        public void execute(String command) {
            if (Math.random() > successProbability) {
                throw new ConnectionException("Error executing command in FaultyConnection: " + command);
            }
            LOGGER.info("Executing command in FaultyConnection: {}", command);
        }

        @Override
        public void close() {
            LOGGER.info("Closing FaultyConnection");
        }
    }

    /**
     * Provides connections with a some chance of being faulty.
     */
    public static class DefaultConnectionManager implements ConnectionManager {

        private static double faultyConnectionProbability = DEFAULT_PROBABILITY;

        void setProbability(double newProbability) {
            faultyConnectionProbability = newProbability;
        }

        @Override
        public Connection getConnection() {
            if (Math.random() > faultyConnectionProbability) {
                return new FaultyConnection();
            } else {
                return new StableConnection();
            }
        }
    }

    /**
     * Always provides faulty connections.
     */
    public static class FaultyConnectionManager implements ConnectionManager {
        @Override
        public Connection getConnection() {
            return new FaultyConnection();
        }
    }
}
