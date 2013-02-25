import java.io.IOException;
import java.io.InputStream;

public class Router {

    public InputStream routeRequest(String request) throws IOException {
        RequestParser requestParser = new RequestParser(request);

        if (requestParser.type().equals("GET")) {
            return getResponse(requestParser);
        }
        else if (requestParser.type().equals("PUT")) {
            return getResponse(requestParser);
        }
        else {
            return new ErrorResponse(500).response();
        }
    }

    public InputStream getResponse(RequestParser requestParser) throws IOException {
        String requestRoute = requestParser.route();
        String rootDirectory = "public";
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (requestRoute.contains("/some-script-url/") || requestRoute.contains("/form/")) {
            return new QueryStringResponse(requestParser).response();
        }
        else if (fileChecker.directoryExists(requestRoute)) {
            return new DirectoryResponse(rootDirectory, requestRoute).response();
        }
        else if (fileChecker.fileExists(requestRoute)) {
            return new FileResponse(rootDirectory, requestRoute).response();
        }
        else {
            return new ErrorResponse(404).response();
        }

    }

}
