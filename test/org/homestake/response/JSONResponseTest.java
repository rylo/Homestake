package org.homestake.response;

import static junit.framework.Assert.*;

import org.homestake.utils.RequestParser;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class JSONResponseTest {
    String responseBody = "{\"json\" : \"true\"}";
    RequestParser requestParser = new RequestParser("GET /api/json HTTP/1.1\n...");
    JSONResponse jsonResponse = new JSONResponse(responseBody);
    HashMap<String, Object> headers = jsonResponse.headerValues();

    @Test
    public void testConstructors() {
        assertNotNull(new JSONResponse());
    }

    @Test
    public void testHeaderValues() throws IOException {
        assertEquals(200, headers.get("status"));
        assertEquals("application/json; charset=utf-8", headers.get("content-type"));
        assertEquals("gzip", headers.get("content-encoding"));
        assertEquals(null, headers.get("content-length"));
    }

    @Test
    public void testGZIPBodyCompression() throws Exception {
        assertNotNull(jsonResponse.response(requestParser).get("2-gzip-body"));
        assertNull(jsonResponse.response(requestParser).get("2-default-body"));
    }

}
