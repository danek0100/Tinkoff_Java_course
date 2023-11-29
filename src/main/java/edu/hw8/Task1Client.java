package edu.hw8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Task1Client {
    private final String serverAddress;
    private final int serverPort;
    private final BufferedReader userInputReader;
    private final PrintWriter outputWriter;
    private final Logger logger;
    private final SocketFactory socketFactory;

    public Task1Client(String serverAddress, int serverPort, BufferedReader userInputReader, PrintWriter outputWriter,
        Logger logger, SocketFactory socketFactory) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.userInputReader = userInputReader;
        this.outputWriter = outputWriter;
        this.logger = logger;
        this.socketFactory = socketFactory;
    }

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

    public interface SocketFactory {
        Socket createSocket(String host, int port) throws IOException;
    }

}
