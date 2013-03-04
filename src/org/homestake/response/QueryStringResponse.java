package org.homestake.response;

import org.homestake.utils.RequestParser;
import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;

public class QueryStringResponse extends ServerResponse {
    private RequestParser requestParser;

    public QueryStringResponse(RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public InputStream response() throws IOException {
        ByteArrayInputStream header = new ByteArrayInputStream(generateDirectoryHeader().getBytes());
        ByteArrayInputStream body = new ByteArrayInputStream(queryStringPrinter(requestParser.queryStrings()).getBytes());
        return new SequenceInputStream(header, body);
    }

    public String queryStringPrinter(Hashtable<String, String> queryStrings) throws UnsupportedEncodingException {
        Enumeration<String> queryStringEnumerator = queryStrings.keys();
        String response = "";
        while(queryStringEnumerator.hasMoreElements()) {
            String key = queryStringEnumerator.nextElement();
            String value = queryStrings.get(key);

            response += decodeParameter(key) + " = " + decodeParameter(value) + "\n";
        }
        return response;
    }

    public String decodeParameter(String string) throws UnsupportedEncodingException {
        return URLDecoder.decode(string, "UTF-8");
    }

}
