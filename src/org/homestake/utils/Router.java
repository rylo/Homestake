package org.homestake.utils;

import org.homestake.response.*;
import java.io.IOException;
import java.io.InputStream;

public class Router {

    public InputStream routeRequest(String requestString) throws IOException {
        RequestParser request = new RequestParser(requestString);

        if (request.method().equals("GET")) {
            return getResponse(request);
        }
        else if (request.method().equals("PUT")) {
            return getResponse(request);
        }
        else if (request.method().equals("POST")){
            return new StatusCodeResponse(200).response();
        }
        else {
            return new StatusCodeResponse(500).response();
        }
    }

    public InputStream getResponse(RequestParser requestParser) throws IOException {
        String requestRoute = requestParser.route();
        String rootDirectory = "public";
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (requestRoute.contains("/some-script-url/") || requestRoute.contains("/form/")) {
            return new QueryStringResponse(requestParser).response();
        }
        else if (requestRoute.contains("/redirect/")) {
            return new RedirectResponse(requestParser.route(), "/").response();
        }
        else if (fileChecker.directoryExists(requestRoute)) {
            return new DirectoryResponse(rootDirectory, requestRoute).response();
        }
        else if (fileChecker.fileExists(requestRoute)) {
            return new FileResponse(rootDirectory, requestRoute).response();
        }
        else {
            return new StatusCodeResponse(404).response();
        }
    }

}
