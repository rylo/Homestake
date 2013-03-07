package org.homestake.response;

import org.homestake.utils.RequestParser;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryStringResponse extends ServerResponse {
    private RequestParser requestParser;

    public QueryStringResponse(RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public HashMap<String, InputStream> response() throws IOException {
        setResponseBody(queryStringPrinter(requestParser.queryStrings()));
        body = new ByteArrayInputStream(responseBody.getBytes());
        mappedResponse.put("default-body", body);

        header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());
        mappedResponse.put("default-header", header);

        return mappedResponse;
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

    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headerValues = new HashMap<String, Object>();
            headerValues.put("status", 200);
            headerValues.put("content-type", "text/plain");
            headerValues.put("content-length", new Long(responseBody.length()));
        return headerValues;
    }

}
