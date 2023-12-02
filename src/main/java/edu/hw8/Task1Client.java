package edu.hw8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Task1Client class handles the client-side operations for a network application.
 * It connects to a server using a socket, sends user input to the server, and processes server responses.
 */
public class Task1Client {
    private final String serverAddress;
    private final int serverPort;
    private final BufferedReader userInputReader;
    private final PrintWriter outputWriter;
    private final Logger logger;
    private final SocketFactory socketFactory;


    /**
     * Constructs a Task1Client with the specified parameters.
     *
     * @param serverAddress The address of the server to connect to.
     * @param serverPort The port number of the server.
     * @param userInputReader A BufferedReader to read user input.
     * @param outputWriter A PrintWriter to write output messages.
     * @param logger A Logger to log information.
     * @param socketFactory A factory to create Socket instances.
     */
    public Task1Client(String serverAddress, int serverPort, BufferedReader userInputReader, PrintWriter outputWriter,
        Logger logger, SocketFactory socketFactory) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.userInputReader = userInputReader;
        this.outputWriter = outputWriter;
        this.logger = logger;
        this.socketFactory = socketFactory;
    }

    /**
     * Starts the client to communicate with the server.
     * This method establishes a connection to the server, sends user input and receives server responses.
     *
     * @throws IOException If an I/O error occurs when creating the socket, reading from or writing to the socket.
     */
    public void startClient() throws IOException {
        try (Socket socket = socketFactory.createSocket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String userInput;
            String response;
            while ((userInput = userInputReader.readLine()) != null) {
                out.println(userInput);
                response = in.readLine();
                logger.info("Ответ сервера: " + response);
                outputWriter.println("Сервер: " + response);
            }
        }
    }

    /**
     * The SocketFactory interface is used to abstract the creation of Socket instances.
     * It allows for different implementations of socket creation logic.
     */
    public interface SocketFactory {

        /**
         * Creates a socket connected to the specified host and port.
         *
         * @param host The hostname or IP address.
         * @param port The port number.
         * @return A new Socket connected to the specified host and port.
         * @throws IOException If an I/O error occurs when creating the socket.
         */
        Socket createSocket(String host, int port) throws IOException;
    }

}
