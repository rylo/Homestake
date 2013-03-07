package org.homestake.response;

import org.homestake.utils.RequestParser;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryStringResponse extends ServerResponse {
    private RequestParser requestParser;

    public QueryStringResponse(RequestParser requestParser) throws UnsupportedEncodingException {
        this.requestParser = requestParser;
        setResponseBody(queryStringPrinter(requestParser.queryStrings()));
    }

    public String queryStringPrinter(Hashtable<String, String> queryStrings) throws UnsupportedEncodingException {
        Enumeration<String> queryStringEnumerator = queryStrings.keys();
        String response = "";
        while(queryStringEnumerator.hasMoreElements()) {
            String key = queryStringEnumerator.nextElement();
            String value = queryStrings.get(key);

            response += requestParser.decode(key) + " = " + requestParser.decode(value) + "\n";
        }
        return response;
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headerValues = new HashMap<String, Object>();
            headerValues.put("status", 200);
            headerValues.put("content-type", "text/plain");
            headerValues.put("content-length", new Long(responseBody.length()));
        return headerValues;
    }

}
