package edu.hw8;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Task1ServerTest {
    private Task1Server.ClientHandlerFactory clientHandlerFactory;
    private Task1Server.IQuoteService quoteService;
    private Logger logger;
    private ServerSocket serverSocket;
    private Socket socket;

    @BeforeEach
    void setUp() throws IOException {
        clientHandlerFactory = mock(Task1Server.ClientHandlerFactory.class);
        quoteService = mock(Task1Server.IQuoteService.class);
        logger = mock(Logger.class);
        serverSocket = mock(ServerSocket.class);
        socket = mock(Socket.class);
        when(serverSocket.accept()).thenReturn(socket);
    }

    @Test
    void testServerAcceptsConnections() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        when(clientHandlerFactory.createClientHandler(any(Socket.class))).thenReturn(mock(Task1Server.ClientHandler.class));
        when(serverSocket.accept()).thenReturn(socket);

        CompletableFuture<Void> serverFuture = CompletableFuture.runAsync(() -> {
            try {
                Task1Server server = new Task1Server(serverSocket, clientHandlerFactory);
                server.startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(1000);

        verify(serverSocket, atLeastOnce()).accept();

        serverFuture.cancel(true);

        try {
            serverFuture.get(1, TimeUnit.SECONDS);
        } catch (CancellationException | ExecutionException ignored) {}
    }
}
