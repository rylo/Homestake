import java.io.*;

public class FileResponse extends ServerResponse {
    String rootDirectory;
    String requestRoute;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
    }

    public InputStream response() {
        try {
            ByteArrayInputStream header = new ByteArrayInputStream(generateFileHeader(rootDirectory + requestRoute).getBytes());
            FileInputStream fileInputStream = new FileInputStream(rootDirectory + requestRoute);

            return new SequenceInputStream(header, fileInputStream);
        }
        catch (FileNotFoundException exception) {
            return new StatusCodeResponse(404).response();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new StatusCodeResponse(500).response();
        }
    }

}
