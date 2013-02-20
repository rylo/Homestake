import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class DirectoryResponse extends ServerResponse {
    String rootDirectory;
    String requestRoute;

    public DirectoryResponse(String rootDirectory, String requestRoute) {
        this.rootDirectory = rootDirectory;
        this.requestRoute = requestRoute;
    }

    public InputStream response() {
        String responseBody = formatList(getDirectoryContents());
        responseBody = HTMLWrap(responseBody);
        InputStream inputStream = new ByteArrayInputStream(responseBody.getBytes());
        return inputStream;
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
            formattedList += "<li>" + filename + "</li>\n";
        }

        formattedList += "</ul>";
        return formattedList;
    }

}
