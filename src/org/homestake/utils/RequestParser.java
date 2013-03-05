package org.homestake.utils;

import java.util.Hashtable;

public class RequestParser {
    private String request;

    public RequestParser(String request) {
        this.request = request;
    }

    public String route() {
        String route = rawRoute();
        route = route.split("\\?")[0];

        if( !route.equals("/") ) {
            route += "/";
        }
        return route;
    }

    public String rawRoute() {
        return header()[1];
    }

    public String[] header() {
        String lines[] = request.split("\\r?\\n");
        return lines[0].split(" ");
    }

    public String method() {
        return header()[0];
    }

    public Hashtable queryStrings() {
        if (queryStringPresent()) {
            String route = rawRoute();
            String rawQueryStrings = route.split("\\?")[1];
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
        return (rawRoute().split("\\?").length > 1);
    }

}
