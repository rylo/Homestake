import java.io.IOException;
import java.io.InputStream;

public class Router {

    public InputStream routeRequest(String request) throws IOException {
        RequestParser requestParser = new RequestParser(request);

        if (requestParser.type().equals("GET")) {
            return getResponse(requestParser.route());
        }
        else {
            return new ErrorResponse(500).response();
        }
    }

    public InputStream getResponse(String requestRoute) throws IOException {
        String rootDirectory = "public";
        FileChecker fileChecker = new FileChecker(rootDirectory);

        if (requestRoute.equals("/some-script-url/")) {
            return new ErrorResponse(200).response();
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
