import java.io.FileInputStream;
import java.io.InputStream;

public class FileResponse extends ServerResponse {
    String rootDirectory;
    String requestRoute;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
    }

    public InputStream response() {
        try {
            return new FileInputStream(rootDirectory + requestRoute);
        }
        catch (Exception exception) {
            return null;
        }
    }

}
