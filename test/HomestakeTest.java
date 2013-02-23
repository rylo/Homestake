import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

import java.io.*;

public class HomestakeTest {
    MockServerSocket mockServerSocket;
    SocketWrapper socketWrapper;
    Homestake homestake;
    String mockRequest;
    MockSocket mockSocket;

    @Before
    public void initialize() throws IOException {
        mockRequest = "GET /rylan/index.html HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        Logger logger = new Logger();
        mockServerSocket = new MockServerSocket(mockRequest);
        socketWrapper = new SocketWrapper(5000, logger, mockServerSocket);
        homestake = new Homestake(socketWrapper);
        mockSocket = new MockSocket(mockRequest);
    }

    @Test
    public void testConstructors() {
        assertNotNull(new Homestake());
        assertNotNull(new Homestake(socketWrapper));
    }

    @Test
    public void testGetServerResponse() throws IOException {
        String response = new SpecHelper().responseString(homestake.getServerResponse(mockSocket));
        assertTrue(response.contains("<h1>O hi!</h1>\n"));
    }

    @Test
    public void testSendResponse() throws IOException {
        homestake.sendResponse(mockSocket, homestake.getServerResponse(mockSocket));
        OutputStream outputStream = mockSocket.getOutputStream();
        assertTrue(outputStream.toString().contains("<h1>O hi!</h1>\n"));
    }

    @Test
    public void testStartServer() throws IOException {
        //homestake.startServer();
        //HOW DO I TEST THIS?! :(
    }

}
