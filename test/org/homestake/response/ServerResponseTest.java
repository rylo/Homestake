package org.homestake.response;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static junit.framework.Assert.*;

public class ServerResponseTest {
    ServerResponse serverResponse = new ServerResponse();

    @Test
    public void testHTMLWrap() {
        assertEquals("<html><body></body></html>", serverResponse.HTMLWrap(""));
        assertEquals("<html><body>TEST-TEST</body></html>", serverResponse.HTMLWrap("TEST-TEST"));
    }

    @Test
    public void testGzipStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("LOL".getBytes());
//        assertEquals("java.util.zip.GZIPOutputStream", serverResponse.gzipStream(inputStream).getClass().getName());
    }

}
