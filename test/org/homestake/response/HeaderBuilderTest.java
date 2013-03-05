package org.homestake.response;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class HeaderBuilderTest {
    HeaderBuilder headerBuilder = new HeaderBuilder();

    @Test
    public void testGenerateContentType() throws IOException {
        assertEquals("Content-Type: image/gif\r\n", headerBuilder.generateContentType("image/gif"));
        assertEquals("Content-Type: text/html; charset=UTF-8\r\n", headerBuilder.generateContentType("text/html"));
        assertEquals("Content-Type: text/plain; charset=UTF-8\r\n", headerBuilder.generateContentType("text/plain"));
    }

    @Test
    public void testStatusGenerator() {
        assertEquals("HTTP/1.1 200 OK\r\n", headerBuilder.generateStatus(200));
        assertEquals("HTTP/1.1 201 Created\r\n", headerBuilder.generateStatus(201));
        assertEquals("HTTP/1.1 404 Not Found\r\n", headerBuilder.generateStatus(404));
        assertEquals("HTTP/1.1 500 Internal Server Error\r\n", headerBuilder.generateStatus(500));
        assertEquals("HTTP/1.1 302 Found\r\n", headerBuilder.generateStatus(302));
    }

    @Test
    public void testGenerateServer() {
        assertEquals("Server: HomestakeServer/0.01\r\n", headerBuilder.generateServerHeader());
    }

    @Test
    public void testGenerateContentLength() {
//        assertEquals("Content-length: 747083\n", headerBuilder.generateContentLength("public/image/test.gif"));
    }

    @Test
    public void testGenerateLocation() {
        assertEquals("Location: http://localhost:5000/\r\n", headerBuilder.generateLocation("/"));
    }

    @Test
    public void testBuild() throws IOException {
        HashMap<String, Object> requiredHeaders = new HashMap<String, Object>();
            requiredHeaders.put("status", 200);
            requiredHeaders.put("content-type", "text/plain");
            requiredHeaders.put("content-length", new Long(2000));

        assertTrue(headerBuilder.build(requiredHeaders).contains("HTTP/1.1 200 OK"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Date: "));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Server: HomestakeServer/0.01"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Content-Type: text/plain; charset=UTF-8"));
        assertTrue(headerBuilder.build(requiredHeaders).contains("Content-length: 2000"));

    }

    @Test
    public void testGenerateDate() {
        assertTrue(headerBuilder.generateDate().length() == 37);
    }

}
