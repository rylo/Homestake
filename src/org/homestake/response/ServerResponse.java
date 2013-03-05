package org.homestake.response;

import java.io.InputStream;

public class ServerResponse {
    protected HeaderBuilder headerBuilder;
    protected String responseBody;
    protected InputStream body;
    protected InputStream header;

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