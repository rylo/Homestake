import java.io.*;
import java.net.URLConnection;

public class ServerResponse {

    public String HTMLWrap(String body) {
        return "<html><body>" + body + "</body></html>";
    }

    public String generateContentType(String filePath) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        String contentType = URLConnection.guessContentTypeFromStream(inputStream);
        inputStream.close();
        return "Content-Type: " + contentType + ";";
    }

}
