package org.homestake.response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.DeflaterInputStream;

public class JSONResponse extends ServerResponse {

    public JSONResponse(String responseBody) {
        this.responseBody = responseBody;
    }

    public HashMap<String, InputStream> response() throws IOException {
        body = new DeflaterInputStream(new ByteArrayInputStream(responseBody.getBytes()));
        mappedResponse.put("default-header", header);

        header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());
        mappedResponse.put("default-header", header);

        return mappedResponse;
    }

    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 200);
            hash.put("content-type", "application/json");
            hash.put("content-length", responseBody.length());
        return hash;
    }
}
