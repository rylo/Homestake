package org.homestake.utils;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import static junit.framework.Assert.*;

public class RequestParserTest {
    RequestParser requestParser;
    String request;
    String genericRoute = "GET / HTTP/1.1\n...";

    @Test
    public void testRoute() {
        request = "GET / HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/", requestParser.route());

        request = "GET /rylan HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/rylan/", requestParser.route());

        request = "GET /rylan/index.html HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/rylan/index.html", requestParser.route());
    }


    @Test
    public void readEntireRequest() throws IOException {
        request = "GET /rylan/index.html HTTP/1.1\nFlerpty: zerp";
        BufferedReader clientInputStream = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));
        requestParser = new RequestParser(clientInputStream);

        assertTrue(requestParser.fullRequest.contains("Flerpty: zerp"));
    }

    @Test
    public void testType() {
        request = "GET / HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/", requestParser.route());
        assertTrue("/" != requestParser.route());
        assertEquals("GET", requestParser.method());
    }

    @Test
    public void testQueryStrings() {
        request = "GET /?foo=bar HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/", requestParser.route());

        Hashtable queryStringHash = requestParser.queryStrings();
        assertTrue(queryStringHash.containsKey("foo"));

        request = "GET /? HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        queryStringHash = requestParser.queryStrings();
        assertFalse(queryStringHash.containsKey("foo"));
    }

    @Test
    public void testRawRoute() {
        request = "GET /rylan?key=value HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertEquals("/rylan?key=value", requestParser.rawRoute());
    }

    @Test
    public void testQueryStringPresent() {
        request = "GET /rylan?key=value HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertTrue(requestParser.queryStringPresent());

        request = "GET /rylan HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);
        assertFalse(requestParser.queryStringPresent());
    }

    @Test
    public void testHeader() {
        request = "GET / HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        requestParser = new RequestParser(request);

        assertEquals("GET", requestParser.header()[0]);
        assertEquals("/", requestParser.header()[1]);
        assertEquals("HTTP/1.1", requestParser.header()[2]);
    }

    @Test
    public void testHasTrailingSlash() {
        requestParser = new RequestParser(genericRoute);

        assertTrue(requestParser.hasTrailingSlash("/rylan/"));
        assertFalse(requestParser.hasTrailingSlash("/index.html"));
    }

    @Test
    public void testHasFileExtension() {
        requestParser = new RequestParser(genericRoute);

        assertFalse(requestParser.hasFileExtension("/rylan/"));
        assertTrue(requestParser.hasFileExtension("/index.html"));
    }

}
