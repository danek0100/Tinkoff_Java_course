package edu.hw8;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task1NetworkTest {

    private Task1Server server;
    private Thread serverThread;
    private Task1Server.ClientHandlerFactory clientHandlerFactory;

    @BeforeEach
    void setUp() {
        Task1Server.IQuoteService quoteService = new Task1Server.QuoteService(new HashMap<>() {{
            put("test", "Test Quote");
        }});
        clientHandlerFactory = new Task1Server.ClientHandlerFactory(quoteService, LogManager.getLogger());


        serverThread = new Thread(() -> {
            try {
                server = new Task1Server(new ServerSocket(1234), clientHandlerFactory);
                server.startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }

    @AfterEach
    void tearDown() {
        if (server != null) {
            server.stopServer();
        }
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }

    @Test
    void testNetworkCommunication() throws IOException {
        try (Socket clientSocket = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            out.println("test");
            String response = in.readLine();

            Assertions.assertEquals("Test Quote", response);
        }
    }
}
