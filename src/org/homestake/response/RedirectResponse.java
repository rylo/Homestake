package org.homestake.response;

import java.io.*;
import java.util.HashMap;

public class RedirectResponse extends ServerResponse {
    private String redirectPath;

    public RedirectResponse(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public HashMap<String, InputStream> response() throws IOException {
        setResponseBody("");
        body = new ByteArrayInputStream(responseBody.getBytes());
        mappedResponse.put("default-body", body);

        header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());
        mappedResponse.put("default-header", header);

        return mappedResponse;
    }

    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 302);
            hash.put("content-length", new Long(responseBody.length()));
            hash.put("location", redirectPath);
        return hash;
    }
}
