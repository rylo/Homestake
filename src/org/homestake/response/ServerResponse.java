package org.homestake.response;

import java.io.InputStream;

public class ServerResponse {
    protected HeaderBuilder headerBuilder;
    protected InputStream header;
    protected InputStream body;

    public ServerResponse() {
        this.headerBuilder = new HeaderBuilder();
    }

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

}