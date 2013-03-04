package org.homestake.response;

import java.io.*;

public class FileResponse extends ServerResponse {
    private String rootDirectory;
    private String requestRoute;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
    }

    public InputStream response() {
        try {
            header = new ByteArrayInputStream(headerBuilder.generateFileHeader(rootDirectory + requestRoute).getBytes());
            body = new FileInputStream(rootDirectory + requestRoute);

            return new SequenceInputStream(header, body);
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
