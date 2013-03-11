package org.homestake.response;

import java.util.HashMap;

public class RedirectResponse extends ServerResponse {
    private String redirectPath;

    public RedirectResponse(String redirectPath) {
        this.redirectPath = redirectPath;
        setResponseBody("o hi");
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 302);
            hash.put("content-length", new Long(responseBody.length()));
            hash.put("location", redirectPath);
        return hash;
    }
}
