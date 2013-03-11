package org.homestake.response;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

class TestResponse extends ServerResponse {

}

public class ServerResponseTest {
    TestResponse testResponse = new TestResponse();

    @Test
    public void testHTMLWrap() {
        assertEquals("<html><body></body></html>", testResponse.HTMLWrap(""));
        assertEquals("<html><body>TEST-TEST</body></html>", testResponse.HTMLWrap("TEST-TEST"));
    }

}
