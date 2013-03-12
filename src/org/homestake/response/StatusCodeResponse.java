package org.homestake.response;

import java.util.HashMap;

public class StatusCodeResponse extends ServerResponse {
    private int code;

    public StatusCodeResponse(int code) {
        this.code = code;
        setResponseBody(HTMLWrap("<h1>Status code: " + Integer.toString(code) + "</h1>"));
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
            headers.put("status", code);
            headers.put("content-type", "text/html");
            headers.put("content-length", new Long(responseBody.length()));
        return headers;
    }
}