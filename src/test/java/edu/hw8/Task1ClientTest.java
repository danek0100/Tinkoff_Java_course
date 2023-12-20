package edu.hw8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Task1ClientTest {

    private BufferedReader inputReader;
    private StringWriter stringWriter;
    private PrintWriter outputWriter;
    private Task1Client.SocketFactory socketFactory;
    private Socket socket;
    private ByteArrayOutputStream outputStream;
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        inputReader = new BufferedReader(new StringReader("тестовый запрос\n"));
        stringWriter = new StringWriter();
        outputWriter = new PrintWriter(stringWriter);
        socketFactory = mock(Task1Client.SocketFactory.class);
        socket = mock(Socket.class);
        outputStream = new ByteArrayOutputStream();
        mockLogger = mock(Logger.class);
    }

    @Test
    void testClientSendsAndReceivesData() throws Exception {
        when(socketFactory.createSocket(anyString(), anyInt())).thenReturn(socket);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("ответ сервера\n".getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Task1Client client = new Task1Client("localhost", 1234, inputReader, outputWriter, mockLogger, socketFactory);
        client.startClient();

        assertThat(stringWriter.toString().trim()).isEqualTo("Сервер: ответ сервера");

        verify(mockLogger, atLeastOnce()).info("Ответ сервера: ответ сервера");

        String sentData = outputStream.toString();
        assertThat(sentData.trim()).isEqualTo("тестовый запрос");
    }
}
