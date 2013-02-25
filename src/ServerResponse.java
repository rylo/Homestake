import java.io.*;
import java.net.URLConnection;

public class ServerResponse {

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

    public String generateFileHeader(String filePath) throws IOException {
        return generateStatus(200) + generateContentType(filePath) + generateContentLength(filePath) + "\n";
    }

    public String generateDirectoryHeader() throws IOException {
        return generateStatus(200) + "\n";
    }

    public String generateErrorHeader(int errorCode) {
        return generateStatus(errorCode);
    }

    public String generateContentType(String filePath) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        String contentType = URLConnection.guessContentTypeFromStream(inputStream);
        if (contentType == null) { contentType = "text/plain"; }
        inputStream.close();

        if(!contentType.contains("image")) {
            contentType += "; charset=UTF-8";
        }

        return "Content-Type: " + contentType + "\n";
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
            case 404: response = "Not Found";
                      break;
            case 500: response = "Internal Server Error";
                      break;
        }

        return "HTTP/1.1 " + Integer.toString(statusCode) + " " + response + "\n";
    }

    public String generateServerHeader() {
        return "Server: HomestakeServer/0.01\n";
    }

    public String generateContentLength(String filePath) {
        Long contentLength = new File(filePath).length();
        return "Content-length: " + Long.toString(contentLength) + "\n";
    }

}