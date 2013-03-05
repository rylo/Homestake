package org.homestake.response;

import org.homestake.SpecHelper;
import static junit.framework.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class StatusCodeResponseTest {
    private int statusCode;
    private StatusCodeResponse statusCodeResponse;

    @Test
    public void testResponseType() throws IOException {
        statusCode = 500;
        statusCodeResponse = new StatusCodeResponse(statusCode);

        assertEquals("java.io.SequenceInputStream", statusCodeResponse.response().getClass().getName());
    }

    @Test
    public void testResponseContents() throws IOException {
        SpecHelper specHelper = new SpecHelper();
        statusCodeResponse = new StatusCodeResponse(400);
        assertTrue((specHelper.responseString(statusCodeResponse.response())).contains("400"));

        statusCodeResponse = new StatusCodeResponse(500);
        assertTrue((specHelper.responseString(statusCodeResponse.response())).contains("500"));
    }

    @Test
    public void testHeaderValues() {
        StatusCodeResponse statusCodeResponse = new StatusCodeResponse(300);
        String responseBody = "<html><head></head><body>STUFF</body></html>";
        statusCodeResponse.setResponseBody(responseBody);
        HashMap<String, Object> headers = statusCodeResponse.headerValues();

        assertEquals(300, headers.get("status"));
        assertEquals(new Long(responseBody.length()), headers.get("content-length"));
    }

}
