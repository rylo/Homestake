package org.homestake.response;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryStringResponse extends ServerResponse {

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
    public void setBody() throws Exception {
        setResponseBody(queryStringPrinter(requestParser.queryStrings()));
        this.body = new ByteArrayInputStream(responseBody.getBytes());
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
