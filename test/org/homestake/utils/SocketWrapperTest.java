package org.homestake.utils;

import org.homestake.MockServerSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static junit.framework.Assert.*;

public class SocketWrapperTest {
    public MockServerSocket mockServerSocket;

    @Before
    public void initialize() throws IOException {
        mockServerSocket = new MockServerSocket("Test");
    }

    public String responseString(InputStream inputStream) throws IOException {
        InputStreamReader input = new InputStreamReader(inputStream);
        int integer;
        char character;
        String response = "";

        while((integer = input.read()) != -1) {
            character = (char) integer;
            response += String.valueOf(character);
        }
        return response;
    }

    @Test
    public void testAccept() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(7473, mockServerSocket);
        Socket server = socketWrapper.accept();
        assertEquals("org.homestake.MockSocket", server.getClass().getName()); // This isn't testing much - figure out how to REALLY test a socket?
    }

    @Test
    public void returnsTrueWhenIsClosed() {
        SocketWrapper socketWrapper = new SocketWrapper(7473, mockServerSocket);
        assertTrue(socketWrapper.isClosed());
    }

    @Test
    public void testClose() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(7473, mockServerSocket);
        Socket server = socketWrapper.accept();
        assertFalse(server.isClosed());
        server.close();
        assertTrue(server.isClosed());
    }

    @Test
    public void testConstructor() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(1234);
        assertEquals(1234, socketWrapper.getLocalPort());
        socketWrapper.close();

        SocketWrapper socketWrapper1 = new SocketWrapper(1234);
        assertEquals(1234, socketWrapper1.getLocalPort());
        socketWrapper.close();

        SocketWrapper socketWrapper2 = new SocketWrapper(1234, mockServerSocket);
        assertEquals(1234, socketWrapper2.getLocalPort());
        assertSame(mockServerSocket, socketWrapper2.serverSocket);
        socketWrapper.close();
    }

    @Test(expected=IllegalArgumentException.class)
    public void raisesErrorOnInvalidPortNumber() {
        new SocketWrapper(19239123);
    }

    @Test
    public void serverAcceptsConnections() throws Exception {
        SocketWrapper serverSocket = new SocketWrapper(9876, mockServerSocket);
        Socket server = serverSocket.accept();

        assertFalse(server.isClosed());

        String response = responseString(server.getInputStream());

        assertEquals("Test", response);
    }

}