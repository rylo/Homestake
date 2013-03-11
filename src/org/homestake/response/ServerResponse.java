package org.homestake.response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class ServerResponse {
    protected HeaderBuilder headerBuilder;
    protected String responseBody;
    protected InputStream header;
    protected String headerCompression = "1-default-header";
    protected InputStream body;
    protected String bodyCompression = "2-default-body";
    protected TreeMap<String, InputStream> mappedResponse = new TreeMap<String, InputStream>();

    protected ServerResponse() {
        this.headerBuilder = new HeaderBuilder();
    }

    public Map<String, InputStream> response() throws IOException {
        body = new ByteArrayInputStream(responseBody.getBytes());
        header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());

        mappedResponse.put(headerCompression, header);
        mappedResponse.put(bodyCompression, body);

        return mappedResponse;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setBodyCompression(String bodyCompression) {
        this.bodyCompression = bodyCompression;
    }

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

    public HashMap<String, Object> headerValues() throws IOException {
        return null;
    }

}