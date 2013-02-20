import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static junit.framework.Assert.*;

public class SocketWrapperTest {
    public Logger logger;
    public MockServerSocket mockServerSocket;

    @Before
    public void initialize() throws IOException {
        logger = new Logger();
        mockServerSocket = new MockServerSocket();
    }

    @Test
    public void testAccept() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(7473, logger, mockServerSocket);
        Socket server = socketWrapper.accept();
        assertEquals("MockSocket", server.getClass().getName()); // This isn't testing much - figure out how to REALLY test a socket?
    }

    @Test
    public void returnsTrueWhenIsClosed() {
        SocketWrapper socketWrapper = new SocketWrapper(7473, logger, mockServerSocket);
        assertTrue(socketWrapper.isClosed());
    }

    @Test
    public void testClose() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(7473, logger, mockServerSocket);
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

        SocketWrapper socketWrapper1 = new SocketWrapper(1234, logger);
        assertSame(logger, socketWrapper1.logger);
        assertEquals(1234, socketWrapper1.getLocalPort());
        socketWrapper.close();

        SocketWrapper socketWrapper2 = new SocketWrapper(1234, logger, mockServerSocket);
        assertSame(logger, socketWrapper2.logger);
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
        SocketWrapper serverSocket = new SocketWrapper(9876, logger, mockServerSocket);
        Socket server = serverSocket.accept();

        assertFalse(server.isClosed());

        InputStreamReader inputStreamReader = new InputStreamReader(server.getInputStream(), "US-ASCII");
        int readInteger = inputStreamReader.read();
        char character = (char) readInteger;
        String string = String.valueOf(character);

        assertEquals("T", string);
    }

}