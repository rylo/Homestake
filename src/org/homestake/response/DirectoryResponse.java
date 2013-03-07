package org.homestake.response;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DirectoryResponse extends ServerResponse {
    private String rootDirectory;
    private String requestRoute;

    public DirectoryResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
        setResponseBody(HTMLWrap(formatList(getDirectoryContents())));
    }

    public ArrayList<String> getDirectoryContents() {
        File[] list = new File(rootDirectory + requestRoute).listFiles();
        ArrayList fileList = new ArrayList<String>();
        if (list.length > 0 && list != null) {
            for(File file : list) { fileList.add(file.getName()); }
        }
        return fileList;
    }

    public String formatList(ArrayList<String> fileList) {
        String formattedList = "<ul>";

        for(String filename : fileList) {
            formattedList += "<li><a href=\"" + requestRoute + filename + "\">" + filename + "</a></li>\n";
        }

        formattedList += "</ul>";
        return formattedList;
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("status", 200);
            hash.put("content-type", "text/html");
            hash.put("content-length", new Long(responseBody.length()));
        return hash;
    }

}