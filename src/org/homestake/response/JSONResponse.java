package org.homestake.response;

import java.util.HashMap;

public class JSONResponse extends ServerResponse {

    public JSONResponse(String responseBody) {
        setBodyCompression("gzip-body");
        setResponseBody(responseBody);
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
