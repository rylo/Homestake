package org.homestake.response;

import org.homestake.utils.FileChecker;

import java.io.*;
import java.util.HashMap;

public class FileResponse extends ServerResponse {
    private String filePath;

    public FileResponse(String rootDirectory, String requestRoute) {
        this.filePath = rootDirectory + requestRoute;
    }

    @Override
    public void setBody() throws FileNotFoundException {
        this.body = new FileInputStream(filePath);
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