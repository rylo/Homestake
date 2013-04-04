package org.homestake.response;

import org.homestake.utils.Logger;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HeaderBuilder {
    private String newline = "\r\n";

    public String build(HashMap<String, Object> headerValues) throws IOException {
        String response = "";

        Logger.addToQueue(Thread.currentThread().getId(),
          response += generateStatus( (Integer) headerValues.get("status")
        ));

        response += generateDate();
        response += generateServerHeader();
        response += generateContentType( (String) headerValues.get("content-type") );
        response += generateContentEncoding( (String) headerValues.get("content-encoding") );
        response += generateContentLength( (Long) headerValues.get("content-length") );
        response += generateLocation( (String) headerValues.get("location") );

        return response + newline;
    }

    public String generateStatus(int statusCode) {
        String response = "";
        switch (statusCode) {
            case 200: response = "OK";
                break;
            case 201: response = "Created";
                break;
            case 302: response = "Found";
                break;
            case 400: response = "Bad Request";
                break;
            case 404: response = "Not Found";
                break;
            case 500: response = "Internal Server Error";
                break;
        }
        return "HTTP/1.1 " + Integer.toString(statusCode) + " " + response + newline;
    }

    public String generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss zz");
        return "Date: " + dateFormat.format(new Date()) + newline;
    }

    public String generateServerHeader() {
        return "Server: HomestakeServer/0.01" + newline;
    }

    public String generateContentType(String contentType) {
        if (contentType == null) { contentType = "text/plain"; }
        if (contentType.contains("text")) { contentType += "; charset=UTF-8"; }

        return "Content-Type: " + contentType + newline;
    }

    public String generateContentEncoding(String encodingType) {
        if (encodingType != null && encodingType.equals("gzip")) {
            return "Content-Encoding: gzip" + newline;
        }
        else {
            return "";
        }
    }

    public String generateContentLength(Long size) {
        if (size == null) {
            return "";
        }
        else {
            return "Content-length: " + Long.toString(size) + newline;
        }
    }

    public String generateLocation(String redirectLocation) {
        if (redirectLocation == null) {
            return "";
        }
        else {
            return "Location: http://localhost:5000" + redirectLocation + newline;
        }
    }

}
