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
import org.apache.logging.log4j.Logger;

public class Task1Server {
    private final ServerSocket serverSocket;
    private static final int PORT = 1234;
    private static final int MAX_CONNECTIONS = 5;
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(MAX_CONNECTIONS);
    private final ClientHandlerFactory clientHandlerFactory;
    private volatile boolean isRunning = true;

    public Task1Server(ServerSocket serverSocket, ClientHandlerFactory clientHandlerFactory) {
        this.serverSocket = serverSocket;
        this.clientHandlerFactory = clientHandlerFactory;
    }

    public void startServer() throws IOException {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = clientHandlerFactory.createClientHandler(clientSocket);
                THREAD_POOL.execute(clientHandler);
            } catch (IOException e) {
                if (!isRunning) {
                    break;
                }
                throw e;
            }
        }
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {

        }
        THREAD_POOL.shutdown();
    }


    public interface IQuoteService {
        String getQuote(String keyword);
    }

    public static class QuoteService implements IQuoteService {
        private final Map<String, String> quotes;

        public QuoteService(Map<String, String> quotes) {
            this.quotes = new HashMap<>(quotes);
        }

        @Override
        public String getQuote(String keyword) {
            return quotes.getOrDefault(keyword.toLowerCase(), "Цитата не найдена");
        }
    }

    public static class ClientHandlerFactory {
        private final IQuoteService quoteService;
        private final Logger logger;

        public ClientHandlerFactory(IQuoteService quoteService, Logger logger) {
            this.quoteService = quoteService;
            this.logger = logger;
        }

        public ClientHandler createClientHandler(Socket socket) {
            return new ClientHandler(socket, quoteService, logger);
        }
    }

    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final IQuoteService quoteService;
        private final Logger logger;

        public ClientHandler(Socket socket, IQuoteService quoteService, Logger logger) {
            this.clientSocket = socket;
            this.quoteService = quoteService;
            this.logger = logger;
        }

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
