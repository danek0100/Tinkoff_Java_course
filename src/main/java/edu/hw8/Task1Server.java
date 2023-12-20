package edu.hw8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.Logger;

/**
 * The Task1Server class represents a server that can handle multiple client connections concurrently.
 * It uses a thread pool to manage client connections and processes client requests.
 */
public class Task1Server {
    private final ServerSocket serverSocket;
    public static final int MAX_CONNECTIONS = 5;
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_CONNECTIONS);
    private final ClientHandlerFactory clientHandlerFactory;
    private volatile boolean isRunning = true;
    private final Semaphore connectionSemaphore = new Semaphore(MAX_CONNECTIONS);


    /**
     * Constructs a new Task1Server.
     *
     * @param serverSocket The ServerSocket to accept client connections.
     * @param clientHandlerFactory Factory for creating client handlers.
     */
    public Task1Server(ServerSocket serverSocket, ClientHandlerFactory clientHandlerFactory) {
        this.serverSocket = serverSocket;
        this.clientHandlerFactory = clientHandlerFactory;
    }

    /**
     * Starts the server to accept and handle client connections.
     * Runs until stopServer() is called.
     *
     * @throws IOException If an I/O error occurs when waiting for a connection.
     */
    public void startServer() throws IOException {
        while (isRunning) {
            try {
                connectionSemaphore.acquire();
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = clientHandlerFactory.createClientHandler(clientSocket);
                THREAD_POOL.execute(() -> {
                    try {
                        clientHandler.run();
                    } finally {
                        connectionSemaphore.release();
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                if (!isRunning) {
                    break;
                }
                throw e;
            }
        }
    }

    /**
     * Stops the server from accepting new connections and shuts down the thread pool.
     */
    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {

        }
        THREAD_POOL.shutdown();
    }


    /**
     * The IQuoteService interface defines a contract for a service that provides quotes.
     */
    public interface IQuoteService {

        /**
         * Retrieves a quote based on the provided keyword.
         *
         * @param keyword The keyword used to search for a quote.
         * @return A quote as a String. If no quote is found for the keyword, a default message is returned.
         */
        String getQuote(String keyword);
    }

    /**
     * The QuoteService class implements the IQuoteService interface, providing functionality to retrieve quotes.
     */
    public static class QuoteService implements IQuoteService {
        private final Map<String, String> quotes;

        /**
         * Constructs a QuoteService with the given map of quotes.
         *
         * @param quotes A map where keys are keywords and values are quotes associated with those keywords.
         */
        public QuoteService(Map<String, String> quotes) {
            this.quotes = new HashMap<>(quotes);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getQuote(String keyword) {
            return quotes.getOrDefault(keyword.toLowerCase(), "Цитата не найдена");
        }
    }

    /**
     * The ClientHandlerFactory class is responsible for creating ClientHandler instances.
     */
    public static class ClientHandlerFactory {
        private final IQuoteService quoteService;
        private final Logger logger;

        /**
         * Constructs a ClientHandlerFactory with a quote service and a logger.
         *
         * @param quoteService The quote service to be used by client handlers.
         * @param logger The logger to be used by client handlers.
         */
        public ClientHandlerFactory(IQuoteService quoteService, Logger logger) {
            this.quoteService = quoteService;
            this.logger = logger;
        }

        /**
         * Creates a new ClientHandler for a given socket.
         *
         * @param socket The socket representing a client connection.
         * @return A new ClientHandler instance.
         */
        public ClientHandler createClientHandler(Socket socket) {
            return new ClientHandler(socket, quoteService, logger);
        }
    }

    /**
     * The ClientHandler class is responsible for handling individual client connections.
     */
    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final IQuoteService quoteService;
        private final Logger logger;

        /**
         * Constructs a ClientHandler with a client socket, quote service, and logger.
         *
         * @param socket The socket for client communication.
         * @param quoteService The quote service for providing quotes to the client.
         * @param logger The logger for logging messages and errors.
         */
        public ClientHandler(Socket socket, IQuoteService quoteService, Logger logger) {
            this.clientSocket = socket;
            this.quoteService = quoteService;
            this.logger = logger;
        }

        /**
         * Listens for client input, retrieves quotes, and sends responses back to the client.
         */
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String quote = quoteService.getQuote(inputLine);
                    out.println(quote);
                }
            } catch (IOException ex) {
                logger.error(ex);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
        }
    }
}
