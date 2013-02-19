import org.junit.Test;

import static junit.framework.Assert.*;

public class RouterTest {
    String mockRequest = "GET / HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\nCookie: _MunroTeam_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRkkiJTUwNDFkMmE0Yzc4Nzk5ZTI0YTA5NTJmMjdkNGQ5YjE2BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStPdTdNWGxrbTRqYWQwYnRsek1ISkdudkVMRnB4cXBHejJOUDNGb2FGbGs9BjsARg%3D%3D--00334785db467818b2052ed3639d0a0a88daf662; rack.session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRiJFMDZmMjhmZjNlMDVjMWRlNDFmYjE3%0AZjJhMTIxNmQ4ZjQ5NDI2MjA2N2Y1YjYwMDk1ZmY0YmFmYjYwZjE3NTZjZkki%0AEmNoZWNrcG9pbnQtaWQGOwBGSSISY2hlY2twb2ludC1pZAY7AEY%3D%0A--6e7a2c6f82540c5f78b3f182e397fc105590286c";
    String mockRequest2 = "GET /test/RouterTest.java HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\nCookie: _MunroTeam_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRkkiJTUwNDFkMmE0Yzc4Nzk5ZTI0YTA5NTJmMjdkNGQ5YjE2BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStPdTdNWGxrbTRqYWQwYnRsek1ISkdudkVMRnB4cXBHejJOUDNGb2FGbGs9BjsARg%3D%3D--00334785db467818b2052ed3639d0a0a88daf662; rack.session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRiJFMDZmMjhmZjNlMDVjMWRlNDFmYjE3%0AZjJhMTIxNmQ4ZjQ5NDI2MjA2N2Y1YjYwMDk1ZmY0YmFmYjYwZjE3NTZjZkki%0AEmNoZWNrcG9pbnQtaWQGOwBGSSISY2hlY2twb2ludC1pZAY7AEY%3D%0A--6e7a2c6f82540c5f78b3f182e397fc105590286c";
    String mockRequest3 = "GET /asdfasdf HTTP/1.1\nHost: localhost:5000\nConnection: keep-alive\nCache-Control: max-age=0\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17\nAccept-Encoding: gzip,deflate,sdch\nAccept-Language: en-US,en;q=0.8\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\nCookie: _MunroTeam_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRkkiJTUwNDFkMmE0Yzc4Nzk5ZTI0YTA5NTJmMjdkNGQ5YjE2BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStPdTdNWGxrbTRqYWQwYnRsek1ISkdudkVMRnB4cXBHejJOUDNGb2FGbGs9BjsARg%3D%3D--00334785db467818b2052ed3639d0a0a88daf662; rack.session=BAh7B0kiD3Nlc3Npb25faWQGOgZFRiJFMDZmMjhmZjNlMDVjMWRlNDFmYjE3%0AZjJhMTIxNmQ4ZjQ5NDI2MjA2N2Y1YjYwMDk1ZmY0YmFmYjYwZjE3NTZjZkki%0AEmNoZWNrcG9pbnQtaWQGOwBGSSISY2hlY2twb2ludC1pZAY7AEY%3D%0A--6e7a2c6f82540c5f78b3f182e397fc105590286c";

    Router router = new Router();

    @Test
    public void testConstructor() {
       new Router();
    }

    @Test
    public void testGetRoute() {
        assertEquals("/", router.parseRoute(mockRequest));
        assertEquals("/test/RouterTest.java", router.parseRoute(mockRequest2));
    }

    @Test
    public void testGetRequestType() {
        assertEquals("GET", router.parseRequestType(mockRequest));
    }

    @Test
    public void testGettingTheCorrectResponseType() {
        assertEquals("DirectoryResponse", router.routeRequest(mockRequest).getClass().getName());
        assertEquals("FileResponse", router.routeRequest(mockRequest2).getClass().getName());
        assertNull(router.routeRequest(mockRequest3));
    }
}
