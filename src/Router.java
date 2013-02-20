import java.io.InputStream;

public class Router {

    public InputStream routeRequest(String request) {
        String requestType = parseRequestType(request);
        System.out.println("Routing request: " + request);

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

    public InputStream buildGetResponse(String requestRoute) {
        String rootDirectory = "public";
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (fileChecker.directoryExists(requestRoute)) {
            return new DirectoryResponse(rootDirectory, requestRoute).response();
        }
        else if (fileChecker.fileExists(requestRoute)) {
            return new FileResponse(rootDirectory, requestRoute).response();
        }
        else {
            return new FileResponse(rootDirectory, "/500.html").response();
        }

    }

}
