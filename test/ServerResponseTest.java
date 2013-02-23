import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;

public class ServerResponseTest {
    ServerResponse serverResponse = new ServerResponse();

    @Test
    public void testHTMLWrap() {
        assertEquals("<html><body></body></html>", serverResponse.HTMLWrap(""));
        assertEquals("<html><body>TEST-TEST</body></html>", serverResponse.HTMLWrap("TEST-TEST"));
    }

    @Test
    public void testGenerateContentType() throws IOException {
        assertEquals("Content-Type: image/gif\n", serverResponse.generateContentType("public/image/test.gif"));
        assertEquals("Content-Type: text/html; charset=UTF-8\n", serverResponse.generateContentType("public/rylan/index.html"));
        assertEquals("Content-Type: text/plain; charset=UTF-8\n", serverResponse.generateContentType("public/rylan/file1"));
    }

    @Test
    public void testStatusGenerator() {
        assertEquals("HTTP/1.1 200 OK\n", serverResponse.generateStatus(200));
        assertEquals("HTTP/1.1 201 Created\n", serverResponse.generateStatus(201));
        assertEquals("HTTP/1.1 404 Not Found\n", serverResponse.generateStatus(404));
        assertEquals("HTTP/1.1 500 Internal Server Error\n", serverResponse.generateStatus(500));
    }

    @Test
    public void testGenerateFileHeader() throws IOException {
        String testHeader = serverResponse.generateStatus(200) + serverResponse.generateContentType("public/image/test.gif") + serverResponse.generateContentLength("public/image/test.gif") + "\n";
        assertEquals(testHeader, serverResponse.generateFileHeader("public/image/test.gif"));
    }

    @Test
    public void testGenerateDirectoryHeader() throws IOException {
        String testHeader = serverResponse.generateStatus(200) + "\n";
        assertEquals(testHeader, serverResponse.generateDirectoryHeader());
    }

    @Test
    public void testGenerateErrorHeader() {
        assertEquals("HTTP/1.1 404 Not Found\n", serverResponse.generateErrorHeader(404));
    }

    @Test
    public void testGenerateServer() {
        assertEquals("Server: HomestakeServer/0.01\n", serverResponse.generateServerHeader());
    }

    @Test
    public void testGenerateContentLength() {
        assertEquals("Content-length: 747083\n", serverResponse.generateContentLength("public/image/test.gif"));
    }

}
