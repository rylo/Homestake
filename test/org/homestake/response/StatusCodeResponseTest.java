package org.homestake.response;

import org.homestake.SpecHelper;
import static junit.framework.Assert.*;

import org.homestake.utils.RequestParser;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class StatusCodeResponseTest {
    RequestParser requestParser = new RequestParser("\"GET /redirect HTTP/1.1\\n...\"");
    private SpecHelper specHelper = new SpecHelper();
    private StatusCodeResponse statusCodeResponse;

    @Test
    public void testResponseContents() throws Exception {
        statusCodeResponse = new StatusCodeResponse(400);
        assertTrue((specHelper.responseString(statusCodeResponse.response(requestParser).get("1-default-header"))).contains("400"));

        statusCodeResponse = new StatusCodeResponse(500);
        assertTrue((specHelper.responseString(statusCodeResponse.response(requestParser).get("1-default-header"))).contains("500"));
    }

    @Test
    public void testHeaderValues() {
        statusCodeResponse = new StatusCodeResponse(300);
        String responseBody = "<html><head></head><body>STUFF</body></html>";
        statusCodeResponse.setResponseBody(responseBody);
        HashMap<String, Object> headers = statusCodeResponse.headerValues();

        assertEquals(300, headers.get("status"));
        assertEquals("text/html", headers.get("content-type"));
        assertEquals(new Long(responseBody.length()), headers.get("content-length"));
    }

}
