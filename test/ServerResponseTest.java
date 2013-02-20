import org.junit.Test;

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
    public void testContentTypeGenerator() throws IOException {
        assertEquals("Content-Type: image/gif;", serverResponse.generateContentType("public/image/test.gif"));
        assertEquals("Content-Type: text/html;", serverResponse.generateContentType("public/rylan/index.html"));
    }
}
