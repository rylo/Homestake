package org.homestake;

import org.homestake.response.FunResponse;
import org.homestake.utils.RequestParser;
import org.homestake.utils.SocketWrapper;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import java.io.*;

public class HomestakeTest {
    static int threadCount;
    MockServerSocket mockServerSocket;
    SocketWrapper socketWrapper;
    Homestake homestake;
    String mockRequest;
    MockSocket mockSocket;
    RequestParser requestParser;

    public void waitForThreads(Homestake homestake, String threadName, int maxIterations) throws InterruptedException {
        int iterations = 0;
        this.threadCount = 0;
        while(threadCount == 0) {
            if(iterations >= maxIterations) {
                throw new RuntimeException("Error: No threads named " + threadName + " started.");
            } else {
                iterations ++;
                Thread.sleep(1);
                this.threadCount = homestake.getThreadCount(threadName);
            }
        }
    }

    @Before
    public void initialize() throws IOException {
        mockRequest = "GET /rylan/index.html HTTP/1.1\n...";
        requestParser = new RequestParser(mockRequest);
        mockServerSocket = new MockServerSocket(mockRequest);
        socketWrapper = new SocketWrapper(5000, mockServerSocket);
        homestake = new Homestake(socketWrapper);
        mockSocket = new MockSocket(mockRequest);
    }

    @Test
    public void testConstructors() {
        assertNotNull(new Homestake());
        assertNotNull(new Homestake(socketWrapper));
    }

    @Test
    public void testRegisterRoute() {
        homestake.registerRoute("lols", new FunResponse());
    }

    @Test
    public void testGetServerResponse() throws Exception {
        String response = new SpecHelper().responseString(homestake.getServerResponse(requestParser).get("2-default-body"));
        assertTrue(response.contains("<h1>O hi!</h1>\n"));
    }

    @Test
    public void testSendResponse() throws Exception {
        homestake.sendResponses(mockSocket, homestake.getServerResponse(requestParser));
        OutputStream outputStream = mockSocket.getOutputStream();
        assertTrue(outputStream.toString().contains("<h1>O hi!</h1>\n"));
    }

    @Test
    public void testDefaults() {
        assertEquals("public", homestake.rootDirectory);
        assertEquals(5000, homestake.port);
    }

    @Test
    public void testMain() throws IOException, InterruptedException {
        final String rootDirectoryArgument = "/Users/rylan/IdeaProjects/Homestake/public";
        final String portArgument = "5050";
        final Homestake homestake1 = new Homestake(socketWrapper);

        // Start the server in a thread to avoid blocking.
        new Thread(new Runnable() {
            @Override
            public void run() {
                homestake1.main(new String[]{"-p", portArgument, "-d", rootDirectoryArgument});
            }
        }, "homestake-thread").start();

        waitForThreads(homestake1, "homestake-thread", 100);

        assertEquals(rootDirectoryArgument, homestake1.rootDirectory);
        assertEquals(Integer.parseInt(portArgument), homestake1.port);
    }

    @Test
    public void testGetResponseThreadCount() throws IOException, InterruptedException {
        assertEquals(0, homestake.getThreadCount("homestake-response-thread"));

        // Start the server in a thread to avoid blocking.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    homestake.startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "homestake-thread").start();

        waitForThreads(homestake, "homestake-response-thread", 100);

        assertTrue(threadCount > 0);
    }

    @Test
    public void testPrepareOutputStream() throws IOException {
        OutputStream outputStream;

        outputStream = homestake.setCompression("knaflskfasdf", new ByteArrayOutputStream());
        assertEquals("java.io.BufferedOutputStream", outputStream.getClass().getName());

        outputStream = homestake.setCompression("gzip-body", new ByteArrayOutputStream());
        assertEquals("java.io.BufferedOutputStream", outputStream.getClass().getName());
    }

}
