package org.homestake.response;

public class ServerResponse {
    public HeaderBuilder headerBuilder;

    public ServerResponse() {
        this.headerBuilder = new HeaderBuilder();
    }

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

}