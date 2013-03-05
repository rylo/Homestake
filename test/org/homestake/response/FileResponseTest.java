package org.homestake.response;

import org.homestake.SpecHelper;
import static junit.framework.Assert.*;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;

public class FileResponseTest {
    private SpecHelper specHelper =  new SpecHelper();

    @Test
    public void testConstructor() throws IOException {
        FileResponse fileResponse = new FileResponse("public", "/rylan/index.html");

        assertEquals("java.io.SequenceInputStream", fileResponse.response().getClass().getName());
    }

    @Test
    public void testNotFoundResponse() throws IOException {
        FileResponse fileResponse = new FileResponse("public", "/asdfa;sdl0asdfa-d=f-23kjfansasdf");
        String response = specHelper.responseString(fileResponse.response());

        assertTrue(response.contains("404") && response.contains("Not Found"));
    }

    @Test
    public void testHeaderValues() throws IOException {
        FileResponse fileResponse = new FileResponse("public", "/rylan/index.html");
        HashMap<String, Object> headers = fileResponse.headerValues();

        assertEquals(200, headers.get("status"));
        assertEquals(null, headers.get("date"));
        assertEquals(null, headers.get("server"));
        assertEquals("text/html", headers.get("content-type"));
        assertEquals(new Long(116), headers.get("content-length"));
    }

}
