package org.homestake.utils;

import java.util.Hashtable;

public class RequestParser {
    private String request;
    public String route;
    public String[] header;
    public String rawRoute;
    public String method;

    public RequestParser(String request) {
        this.request = request;
        this.header = header();
        this.rawRoute = rawRoute();
        this.route = route();
        this.method = method();
    }

    public String route() {
        String route;
        route = this.rawRoute.split("\\?")[0];
        if( hasTrailingSlash(route) || hasFileExtension(route)) {
            return route;
        }
        else {
            return route + "/";
        }
    }

    public boolean hasFileExtension(String filePath) {
        int charactersAfterPeriod = (filePath.length() - 1) - filePath.lastIndexOf(".");
        return charactersAfterPeriod >= 2 && charactersAfterPeriod <= 4;
    }

    public boolean hasTrailingSlash(String filePath) {
        return filePath.lastIndexOf("/") == filePath.length() - 1;
    }

    public String rawRoute() {
        return header[1];
    }

    public String[] header() {
        String lines[] = request.split("\\r?\\n");
        return lines[0].split(" ");
    }

    public String method() {
        return header[0];
    }

    public Hashtable queryStrings() {
        if (queryStringPresent()) {
            String rawQueryStrings = rawRoute.split("\\?")[1];
            String[] queryStrings = rawQueryStrings.split("&");

            Hashtable<String, String> queryStringHash = new Hashtable<String, String>();
            for(String string : queryStrings) {
                if(string.contains("=")) {
                    String[] queryString = string.split("=");
                    queryStringHash.put(queryString[0], queryString[1]);
                }
            }
            return queryStringHash;
        } else {
            return new Hashtable<String, String>();
        }
    }

    public boolean queryStringPresent() {
        return (rawRoute.split("\\?").length > 1);
    }

}
