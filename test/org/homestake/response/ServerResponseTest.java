package org.homestake.response;

import org.junit.Test;
import static junit.framework.Assert.*;

public class ServerResponseTest {
    ServerResponse serverResponse = new ServerResponse();

    @Test
    public void testHTMLWrap() {
        assertEquals("<html><body></body></html>", serverResponse.HTMLWrap(""));
        assertEquals("<html><body>TEST-TEST</body></html>", serverResponse.HTMLWrap("TEST-TEST"));
    }

}
