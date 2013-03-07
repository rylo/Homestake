package org.homestake.utils;

import org.homestake.SpecHelper;
import org.junit.Test;
import static junit.framework.Assert.*;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

public class RouterTest {
    SpecHelper specHelper = new SpecHelper();
    String mockRequest;
    Router router = new Router("public");

    @Test
    public void testConstructor() {
       new Router("public");
    }

    @Test
    public void testGettingTheCorrectResponseType() throws IOException {
        mockRequest = "GET / HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("200 OK"));

        mockRequest = "GET /rylan/index.html HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("200 OK"));

        mockRequest = "GET /asdfasdf HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("404 Not Found"));

        mockRequest = "POST /asdfasdf HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("200 OK"));

        mockRequest = "GET /some-script-url?key=value HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("200 OK"));

        mockRequest = "GET /form HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("200 OK"));

        mockRequest = "GERT /form HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(specHelper.responseString(router.routeRequest(mockRequest)).contains("400 Bad Request"));
    }

}
