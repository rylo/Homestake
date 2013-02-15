import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class SocketHandlerTest {
    public Logger logger;
    public MockServerSocket mockServerSocket;


    @Before
    public void initialize() throws IOException {
        logger = new Logger();
        mockServerSocket = new MockServerSocket();
    }

    @Test
    public void testAccept() throws IOException {
        SocketHandler socketHandler = new SocketHandler(7473, logger, mockServerSocket);
        Socket server = socketHandler.accept();
        assertEquals("java.net.Socket", server.getClass().getName()); // This isn't testing much - figure out how to REALLY test a socket?
    }

    @Test
    public void testConstructor() throws IOException {
        SocketHandler socketHandler = new SocketHandler(1234);
        socketHandler.close();

        SocketHandler socketHandler1 = new SocketHandler(1234, logger);
        assertSame(logger, socketHandler1.logger);
        socketHandler.close();

        SocketHandler socketHandler2 = new SocketHandler(1234, logger, mockServerSocket);
        assertSame(logger, socketHandler2.logger);
        assertSame(mockServerSocket, socketHandler2.serverSocket);
        socketHandler.close();
    }

}