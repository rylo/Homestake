package org.homestake.utils;

import org.homestake.response.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private String rootDirectory;
    public Map<String, RegisteredRoute> routes = new HashMap<String, RegisteredRoute>();

    public Router(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        registerRoutes();
    }

    public void registerRoutes() {
        registerRoute("/some-script-url/", new QueryStringResponse());
        registerRoute("/form/", new QueryStringResponse());
        registerRoute("/redirect", new RedirectResponse("/"));
        registerRoute("/api/json", new JSONResponse("{\n\"JSON\" : \"Rocks!\"\n}"));
    }

    public void registerRoute(String route, ServerResponse response) {
        routes.put(route, new RegisteredRoute(route, response));
    }

    public boolean hasRegisteredRoute(String requestRoute) {
        return routes.containsKey(requestRoute);
    }

    public Map<String, InputStream> routeRequest(RequestParser request) throws Exception {
        if (request.method().equals("GET") || request.method().equals("PUT")) {
            return getResponse(request);
        }
        else if (request.method().equals("POST")){
            return new StatusCodeResponse(200).response(request);
        }
        else {
            return new StatusCodeResponse(400).response(request);
        }
    }

    public Map<String, InputStream> getResponse(RequestParser request) throws Exception {
        String requestRoute = request.route;
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (hasRegisteredRoute(requestRoute)) {
            ServerResponse serverResponse = routes.get(requestRoute).response;
            return serverResponse.response(request);
        }
        else if (fileChecker.directoryExists(requestRoute)) {
            return new DirectoryResponse(rootDirectory, requestRoute).response(request);
        }
        else if (fileChecker.fileExists(requestRoute)) {
            return new FileResponse(rootDirectory, requestRoute).response(request);
        }
        else {
            return new StatusCodeResponse(404).response(request);
        }
    }

}
