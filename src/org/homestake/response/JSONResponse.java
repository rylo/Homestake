package org.homestake.response;

import java.util.HashMap;

public class JSONResponse extends ServerResponse {

    public JSONResponse(String responseBody) {
        setResponseBody(responseBody);
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 200);
            hash.put("content-type", "application/json");
            hash.put("content-length", responseBody.length());
        return hash;
    }
}
