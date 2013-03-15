package org.homestake.response;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class HeaderBuilderTest {
    HeaderBuilder headerBuilder = new HeaderBuilder();
    String newline = "\r\n";

    @Test
    public void testGenerateContentType() throws IOException {
        assertEquals("Content-Type: image/gif" + newline, headerBuilder.generateContentType("image/gif"));
        assertEquals("Content-Type: text/html; charset=UTF-8" + newline, headerBuilder.generateContentType("text/html"));
        assertEquals("Content-Type: text/plain; charset=UTF-8" + newline, headerBuilder.generateContentType("text/plain"));
    }

    @Test
    public void testStatusGenerator() {
        assertEquals("HTTP/1.1 200 OK" + newline, headerBuilder.generateStatus(200));
        assertEquals("HTTP/1.1 201 Created" + newline, headerBuilder.generateStatus(201));
        assertEquals("HTTP/1.1 404 Not Found" + newline, headerBuilder.generateStatus(404));
        assertEquals("HTTP/1.1 500 Internal Server Error" + newline, headerBuilder.generateStatus(500));
        assertEquals("HTTP/1.1 302 Found" + newline, headerBuilder.generateStatus(302));
    }

    @Test
    public void testGenerateServer() {
        assertEquals("Server: HomestakeServer/0.01" + newline, headerBuilder.generateServerHeader());
    }

    @Test
    public void testGenerateContentLength() {
        assertEquals("Content-length: 200" + newline, headerBuilder.generateContentLength(new Long(200)));
    }

    @Test
    public void testGenerateLocation() {
        assertEquals("Location: http://localhost:5000/" + newline, headerBuilder.generateLocation("/"));
    }

    @Test
    public void testBuild() throws IOException {
        HashMap<String, Object> requiredHeaders = new HashMap<String, Object>();
            requiredHeaders.put("status", 200);
            requiredHeaders.put("content-type", "text/plain");
            requiredHeaders.put("content-transfer-encoding", "binary");
            requiredHeaders.put("content-length", new Long(2000));

        assertTrue(headerBuilder.build(requiredHeaders).contains("HTTP/1.1 200 OK"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Date: "));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Server: HomestakeServer/0.01"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Content-Type: text/plain; charset=UTF-8"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Content-length: 2000"));
    }

    @Test
    public void testGenerateDate() {
        assertTrue(headerBuilder.generateDate().contains("Date: "));
        assertTrue(headerBuilder.generateDate().length() == 37);
    }

}
