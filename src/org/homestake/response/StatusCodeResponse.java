package org.homestake.response;

import java.io.*;
import java.util.HashMap;

public class StatusCodeResponse extends ServerResponse {
    private int code;

    public StatusCodeResponse(int code) {
        this.code = code;
    }

    public HashMap<String, InputStream> response() throws IOException {
        setResponseBody(HTMLWrap("<h1>Error code: " + Integer.toString(code) + "</h1>"));
        body = new ByteArrayInputStream(responseBody.getBytes());
        mappedResponse.put("default-body", body);

        header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());
        mappedResponse.put("default-header", header);

        return mappedResponse;
    }

    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", code);
            hash.put("content-type", "text/html");
            hash.put("content-length", new Long(responseBody.length()));
        return hash;
    }

}