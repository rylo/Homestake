package org.homestake.response;

import java.util.HashMap;

public class StatusCodeResponse extends ServerResponse {
    private int code;

    public StatusCodeResponse(int code) {
        this.code = code;
        setResponseBody(HTMLWrap("<h1>Error code: " + Integer.toString(code) + "</h1>"));
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", code);
            hash.put("content-type", "text/html");
            hash.put("content-length", new Long(responseBody.length()));
        return hash;
    }
}