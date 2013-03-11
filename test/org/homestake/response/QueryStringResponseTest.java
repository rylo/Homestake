package org.homestake.response;

import org.homestake.SpecHelper;
import org.homestake.utils.RequestParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.junit.Test;
import static junit.framework.Assert.*;

public class QueryStringResponseTest {
    SpecHelper specHelper = new SpecHelper();
    String request = "GET /some-script-url?pirate=pegleg&ninja=shuriken HTTP/1.1\nHost: localhost:5000\n...";
    RequestParser requestParser = new RequestParser(request);
    QueryStringResponse queryStringResponse;

    public QueryStringResponseTest() throws UnsupportedEncodingException {
        queryStringResponse = new QueryStringResponse(requestParser);
    }

    @Test
    public void testResponse() throws IOException {
        String response = specHelper.responseString(queryStringResponse.response().get("2-default-body"));
        assertTrue(response.contains("pirate"));
        assertTrue(response.contains("pegleg"));
    }

    @Test
    public void testQueryStringPrinter() throws UnsupportedEncodingException {
        String response = queryStringResponse.queryStringPrinter(requestParser.queryStrings());
        assertTrue(response.contains("pirate = pegleg"));
        assertTrue(response.contains("ninja = shuriken"));
    }

    @Test
    public void testParameterDecoding() throws UnsupportedEncodingException {
        String request = "GET /form?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F HTTP/1.1\nHost: localhost:5000\n...";
        RequestParser requestParser = new RequestParser(request);
        String response = queryStringResponse.queryStringPrinter(requestParser.queryStrings());
        assertTrue(response.contains("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
    }

    @Test
    public void testHeaderValues() {
        String responseBody = "<html><head></head><body>STUFF</body></html>";
        queryStringResponse.setResponseBody(responseBody);
        HashMap<String, Object> headers = queryStringResponse.headerValues();

        assertEquals(200, headers.get("status"));
        assertEquals(new Long(responseBody.length()), headers.get("content-length"));
    }

}