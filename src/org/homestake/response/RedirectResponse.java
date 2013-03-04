package org.homestake.response;

import org.homestake.response.ServerResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

public class RedirectResponse extends ServerResponse {
    private String requestPath;
    private String redirectPath;

    public RedirectResponse(String requestPath, String redirectPath) {
        this.requestPath = requestPath;
        this.redirectPath = redirectPath;
    }

    public InputStream response() throws IOException {
        ByteArrayInputStream body = new ByteArrayInputStream("STUFF".getBytes());
        ByteArrayInputStream header = new ByteArrayInputStream(generateRedirectHeader(requestPath, redirectPath).getBytes());
        return new SequenceInputStream(header, body);
    }
}
