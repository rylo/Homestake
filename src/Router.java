public class Router {

    public ServerResponse routeRequest(String request) {
        String requestType = parseRequestType(request);

        if (requestType.equals("GET")) {
            return buildGetResponse(parseRoute(request));
        }
        else {
            return null;
        }
    }

    public String parseRoute(String request) {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        String route = header[1];
        return route;
    }

    public String parseRequestType(String request) {
        String lines[] = request.split("\\r?\\n");
        String[] header = lines[0].split(" ");
        String requestType = header[0];
        return requestType;
    }

    public ServerResponse buildGetResponse(String requestRoute) {
        FileChecker fileChecker = new FileChecker();
        System.out.println(requestRoute);

        if (fileChecker.directoryExists("", requestRoute)) {
            return new DirectoryResponse(requestRoute);
        }
        else if (fileChecker.fileExists(requestRoute)) {
            return new FileResponse(requestRoute);
        }
        else {
            return null;
        }

    }

}
