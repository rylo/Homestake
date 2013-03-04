package org.homestake.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;

public class StatusCodeResponse extends ServerResponse {
    private int code;

    public StatusCodeResponse(int code) {
        this.code = code;
    }

    public InputStream response() {
        String responseBody = HTMLWrap("<h1>Error code: " + Integer.toString(code) + "</h1>");

        body = new ByteArrayInputStream(responseBody.getBytes());
        header = new ByteArrayInputStream(headerBuilder.generateErrorHeader(code).getBytes());

        return new SequenceInputStream(header, body);
    }

}