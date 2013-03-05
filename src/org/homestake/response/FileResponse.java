package org.homestake.response;

import java.io.*;
import java.net.URLConnection;
import java.util.HashMap;

public class FileResponse extends ServerResponse {
    private String filePath;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.filePath = rootDirectory + requestRoute;
    }

    public InputStream response() throws IOException {
        try {
            body = new FileInputStream(filePath);
            header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());

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

    public String generateFileMimeType() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        String contentType = URLConnection.guessContentTypeFromStream(inputStream);

        return contentType;
    }

    public HashMap<String, Object> headerValues() throws IOException {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 200);
            hash.put("content-type", generateFileMimeType());
            hash.put("content-length", new File(filePath).length());
        return hash;
    }

}