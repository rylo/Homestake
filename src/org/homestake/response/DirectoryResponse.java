package org.homestake.response;

import org.homestake.response.ServerResponse;

import java.io.*;
import java.util.ArrayList;

public class DirectoryResponse extends ServerResponse {
    String rootDirectory;
    String requestRoute;

    public DirectoryResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
    }

    public InputStream response() throws IOException {
        String responseBody = HTMLWrap(formatList(getDirectoryContents()));

        ByteArrayInputStream body = new ByteArrayInputStream(responseBody.getBytes());
        ByteArrayInputStream header = new ByteArrayInputStream(headerBuilder.generateDirectoryHeader().getBytes());

        return new SequenceInputStream(header, body);
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

}