package org.homestake.response;

import org.homestake.SpecHelper;
import org.junit.Test;
import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

public class RedirectResponseTest {
    SpecHelper specHelper = new SpecHelper();
    RedirectResponse redirectResponse;

    @Test
    public void testResponse() throws IOException {
        redirectResponse = new RedirectResponse("/");
        String response = specHelper.responseString(redirectResponse.response().get("1-default-header"));
        assertTrue(response.contains("302 Found"));
        assertTrue(response.contains("Location:"));
    }

    @Test
    public void testHeaderValues() {
        RedirectResponse redirectResponse = new RedirectResponse("/aksdmfls");
        String responseBody = "<html><head></head><body>STUFF</body></html>";
        redirectResponse.setResponseBody(responseBody);
        HashMap<String, Object> headers = redirectResponse.headerValues();

        assertEquals(302, headers.get("status"));
        assertEquals(new Long(responseBody.length()), headers.get("content-length"));
        assertEquals("/aksdmfls", headers.get("location"));
    }
}
