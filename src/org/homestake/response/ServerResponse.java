package org.homestake.response;

import java.io.*;
import java.util.HashMap;

public class ServerResponse {
    protected HeaderBuilder headerBuilder;
    protected String responseBody;
    protected InputStream body;
    protected InputStream header;
    protected HashMap<String, InputStream> mappedResponse = new HashMap<String, InputStream>();

    protected ServerResponse() {
        this.headerBuilder = new HeaderBuilder();
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

}