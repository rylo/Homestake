package org.homestake.utils;

import org.homestake.response.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Router {
    private String rootDirectory;

    public Router(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public HashMap<String, InputStream> routeRequest(String requestString) throws IOException {
        RequestParser request = new RequestParser(requestString);

        if (request.method().equals("GET") || request.method().equals("PUT")) {
            return getResponse(request);
        }
        else if (request.method().equals("POST")){
            return new StatusCodeResponse(200).response();
        }
        else {
            return new StatusCodeResponse(400).response();
        }
    }

    public HashMap<String, InputStream> getResponse(RequestParser requestParser) throws IOException {
        String requestRoute = requestParser.route();
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (requestRoute.contains("/some-script-url/") || requestRoute.contains("/form/")) {
            return new QueryStringResponse(requestParser).response();
        }
        else if (requestRoute.contains("/redirect/")) {
            return new RedirectResponse("/").response();
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
