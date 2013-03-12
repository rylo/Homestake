package org.homestake.response;

import org.homestake.utils.FileChecker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileResponse extends ServerResponse {
    private String filePath;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.filePath = rootDirectory + requestRoute;
    }

    @Override
    public Map<String, InputStream> response() throws IOException {
        try {
            body = new FileInputStream(filePath);
            mappedResponse.put(bodyCompression, body);

            header = new ByteArrayInputStream(headerBuilder.build(headerValues()).getBytes());
            mappedResponse.put(headerCompression, header);

            return mappedResponse;
        }
        catch (FileNotFoundException exception) {
            return new StatusCodeResponse(404).response();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new StatusCodeResponse(500).response();
        }
    }

    @Override
    public HashMap<String, Object> headerValues() throws IOException {
        FileChecker fileChecker = new FileChecker("public");

        HashMap<String, Object> headers = new HashMap<String, Object>();
            headers.put("status", 200);
            headers.put("content-type", fileChecker.generateFileMimeType(filePath));
            headers.put("content-length", new File(filePath).length());
        return headers;
    }

}