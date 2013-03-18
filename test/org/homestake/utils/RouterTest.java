package org.homestake.utils;

import org.homestake.SpecHelper;
import org.homestake.response.FileResponse;
import org.homestake.response.JSONResponse;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

public class RouterTest {
    SpecHelper specHelper = new SpecHelper();
    String mockRequest;
    Router router;

    public RouterTest() throws Exception {
        router = new Router("public");
    }

    public String responseHeader(String mockRequest) throws Exception {
        return specHelper.responseString(router.routeRequest(mockRequest).get("1-default-header"));
    }

    @Test
    public void testConstructor() throws Exception {
       new Router("public");
    }

    @Test
    public void getsTheCorrectResponseType() throws Exception {
        mockRequest = "GET / HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("200 OK"));

        mockRequest = "GET /rylan/index.html HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("200 OK"));

        mockRequest = "GET /asdfasdf HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("404 Not Found"));

        mockRequest = "POST /asdfasdf HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("200 OK"));

        mockRequest = "GET /some-script-url?key=value HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("200 OK"));

        mockRequest = "GET /form HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("200 OK"));

        mockRequest = "GERT /form HTTP/1.1\nHost: localhost:5000\n...";
        assertTrue(responseHeader(mockRequest).contains("400 Bad Request"));
    }

    @Test
    public void hasRegisteredRoute() {
        String testRequestRoute = "/test-test-test";
        assertFalse(router.hasRegisteredRoute(testRequestRoute));
        router.registerRoute(testRequestRoute, new JSONResponse("{}"));
        assertTrue(router.hasRegisteredRoute(testRequestRoute));
    }

}
