package org.homestake.response;

import static junit.framework.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class JSONResponseTest {

    @Test
    public void testHeaderValues() throws IOException {
        String responseBody = "{\"json\" : \"true\"}";
        JSONResponse jsonResponse = new JSONResponse(responseBody);
        HashMap<String, Object> headers = jsonResponse.headerValues();

        assertEquals(200, headers.get("status"));
        assertEquals("application/json", headers.get("content-type"));
        assertEquals(responseBody.length(), headers.get("content-length"));
    }

}
