package org.homestake.response;

import java.util.HashMap;

public class RedirectResponse extends ServerResponse {
    private String redirectPath;

    public RedirectResponse(String redirectPath) {
        this.redirectPath = redirectPath;
        setResponseBody("");
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
            headers.put("status", 302);
            headers.put("content-length", new Long(responseBody.length()));
            headers.put("location", redirectPath);
        return headers;
    }
}
