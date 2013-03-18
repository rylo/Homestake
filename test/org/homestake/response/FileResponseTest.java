package org.homestake.response;

import static junit.framework.Assert.*;

import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;

public class FileResponseTest {

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
