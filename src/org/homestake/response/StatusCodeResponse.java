package org.homestake.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;

public class StatusCodeResponse extends ServerResponse {
    private int errorCode;

    public StatusCodeResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public InputStream response() {
        String htmlResponse = HTMLWrap("<h1>Error code: " + Integer.toString(errorCode) + "</h1>");
        ByteArrayInputStream body = new ByteArrayInputStream(htmlResponse.getBytes());
        ByteArrayInputStream header = new ByteArrayInputStream(headerBuilder.generateErrorHeader(errorCode).getBytes());

        return new SequenceInputStream(header, body);
    }

}