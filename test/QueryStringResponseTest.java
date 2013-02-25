import org.junit.Test;
import static junit.framework.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class QueryStringResponseTest {
    SpecHelper specHelper = new SpecHelper();
    String request = "GET /some-script-url?pirate=yarr&ninja=silence HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\nCookie: _MunroTeam_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRkkiJTUwNDFkMmE0Yzc4Nzk5ZTI0YTA5NTJmMjdkNGQ5YjE2BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStPdTdNWGxrbTRqYWQwYnRsek1ISkdudkVMRnB4cXBHejJOUDNGb2FGbGs9BjsARg%3D%3D--00334785db467818b2052ed3639d0a0a88daf662; rack.session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRiJFMDZmMjhmZjNlMDVjMWRlNDFmYjE3%0AZjJhMTIxNmQ4ZjQ5NDI2MjA2N2Y1YjYwMDk1ZmY0YmFmYjYwZjE3NTZjZkki%0AEmNoZWNrcG9pbnQtaWQGOwBGSSISY2hlY2twb2ludC1pZAY7AEY%3D%0A--6e7a2c6f82540c5f78b3f182e397fc105590286c";
    RequestParser requestParser = new RequestParser(request);
    QueryStringResponse queryStringResponse = new QueryStringResponse(requestParser);

    @Test
    public void testResponse() throws IOException {
        String response = specHelper.responseString(queryStringResponse.response());
        assertTrue(response.contains("pirate"));
        assertTrue(response.contains("yarr"));
    }

    @Test
    public void testQueryStringPrinter() throws UnsupportedEncodingException {
        String response = queryStringResponse.queryStringPrinter(requestParser.queryStrings());
        assertTrue(response.contains("pirate = yarr"));
        assertTrue(response.contains("ninja = silence"));
    }

    @Test
    public void testParameterDecoding() throws UnsupportedEncodingException {
        String request = "GET /form?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\nCookie: _MunroTeam_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRkkiJTUwNDFkMmE0Yzc4Nzk5ZTI0YTA5NTJmMjdkNGQ5YjE2BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStPdTdNWGxrbTRqYWQwYnRsek1ISkdudkVMRnB4cXBHejJOUDNGb2FGbGs9BjsARg%3D%3D--00334785db467818b2052ed3639d0a0a88daf662; rack.session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRiJFMDZmMjhmZjNlMDVjMWRlNDFmYjE3%0AZjJhMTIxNmQ4ZjQ5NDI2MjA2N2Y1YjYwMDk1ZmY0YmFmYjYwZjE3NTZjZkki%0AEmNoZWNrcG9pbnQtaWQGOwBGSSISY2hlY2twb2ludC1pZAY7AEY%3D%0A--6e7a2c6f82540c5f78b3f182e397fc105590286c";
        RequestParser requestParser = new RequestParser(request);
        String response = queryStringResponse.queryStringPrinter(requestParser.queryStrings());
        assertTrue(response.contains("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
    }

}