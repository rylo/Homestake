package org.homestake.response;

import org.junit.Test;

import java.io.IOException;
import static junit.framework.Assert.assertEquals;

public class HeaderBuilderTest {
    HeaderBuilder headerBuilder = new HeaderBuilder();

    @Test
    public void testGenerateContentType() throws IOException {
        assertEquals("Content-Type: image/gif\n", headerBuilder.generateContentType("public/image/test.gif"));
        assertEquals("Content-Type: text/html; charset=UTF-8\n", headerBuilder.generateContentType("public/rylan/index.html"));
        assertEquals("Content-Type: text/plain; charset=UTF-8\n", headerBuilder.generateContentType("public/rylan/file1"));
    }

    @Test
    public void testStatusGenerator() {
        assertEquals("HTTP/1.1 200 OK\n", headerBuilder.generateStatus(200));
        assertEquals("HTTP/1.1 201 Created\n", headerBuilder.generateStatus(201));
        assertEquals("HTTP/1.1 404 Not Found\n", headerBuilder.generateStatus(404));
        assertEquals("HTTP/1.1 500 Internal Server Error\n", headerBuilder.generateStatus(500));
        assertEquals("HTTP/1.1 302 Found\n", headerBuilder.generateStatus(302));
    }

    @Test
    public void testGenerateFileHeader() throws IOException {
        String testHeader = headerBuilder.generateStatus(200) + headerBuilder.generateContentType("public/image/test.gif") + headerBuilder.generateContentLength("public/image/test.gif") + "\n";
        assertEquals(testHeader, headerBuilder.generateFileHeader("public/image/test.gif"));
    }

    @Test
    public void testGenerateDirectoryHeader() throws IOException {
        String testHeader = headerBuilder.generateStatus(200) + "\n";
        assertEquals(testHeader, headerBuilder.generateDirectoryHeader());
    }

    @Test
    public void testGenerateErrorHeader() {
        assertEquals("HTTP/1.1 404 Not Found\n", headerBuilder.generateErrorHeader(404));
    }

    @Test
    public void testGenerateRedirectHeader() throws IOException {
        String testHeader = "HTTP/1.1 302 Found\n" + "Location: http://localhost:5000/\n" + "Content-type: text/plain; charset=UTF-8\n" + "Content-length: 0\n\n";
        assertEquals(testHeader, headerBuilder.generateRedirectHeader("/redirect/", "/"));
    }

    @Test
    public void testGenerateServer() {
        assertEquals("Server: HomestakeServer/0.01\n", headerBuilder.generateServerHeader());
    }

    @Test
    public void testGenerateContentLength() {
        assertEquals("Content-length: 747083\n", headerBuilder.generateContentLength("public/image/test.gif"));
    }

    @Test
    public void testGenerateLocation() {
        assertEquals("Location: http://localhost:5000/\n", headerBuilder.generateLocation("/"));
    }

}
