package org.homestake.response;

import java.util.HashMap;

public class JSONResponse extends ServerResponse {

    public JSONResponse(String responseBody) {
        setBodyCompression("2-gzip-body");
        setResponseBody(responseBody);
    }

    public JSONResponse() {
        setBodyCompression("2-gzip-body");
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
            headers.put("status", 200);
            headers.put("content-type", "application/json; charset=utf-8");
            headers.put("content-encoding", "gzip");
        return headers;
    }
}
