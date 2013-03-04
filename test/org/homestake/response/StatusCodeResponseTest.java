package org.homestake.response;

import org.homestake.SpecHelper;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;

public class StatusCodeResponseTest {
    private int statusCode;
    private StatusCodeResponse statusCodeResponse;

    @Test
    public void testConstructor() {
        statusCode = 500;
        statusCodeResponse = new StatusCodeResponse(statusCode);

        assertEquals("org.homestake.response.StatusCodeResponse", statusCodeResponse.getClass().getName());
    }

    @Test
    public void testResponseType() {
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

}
