package org.homestake.utils;

import java.io.*;
import java.util.HashMap;

public class FileChecker {
    private String rootDirectory;

    public FileChecker(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public boolean directoryExists(String directory) {
        boolean exists = fileExists(directory);

        if (exists & new File(rootDirectory + directory).isDirectory()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean fileExists(String directory) {
        File file = new File(rootDirectory + directory);
        return file.exists();
    }

    public String generateFileMimeType(String filePath) throws IOException {
        String extension = getFileExtension(filePath);
        String contentType = supportedFileTypes().get(extension);
        return contentType;
    }

    public HashMap<String, String> supportedFileTypes() {
        HashMap<String, String> supportedFileTypes = new HashMap<String, String>();
            supportedFileTypes.put("mp4", "video/mp4");
            supportedFileTypes.put("mp3", "audio/mpeg");
            supportedFileTypes.put("html", "text/html");
            supportedFileTypes.put("pdf", "application/pdf");
            supportedFileTypes.put("jpeg", "image/jpeg");
            supportedFileTypes.put("jpg", "image/jpeg");
            supportedFileTypes.put("gif", "image/gif");

        return supportedFileTypes;
    }

    public String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf(".");
        return filePath.substring(index + 1);
    }

}
