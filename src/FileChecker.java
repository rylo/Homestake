import java.io.File;

public class FileChecker {

    public boolean directoryExists(String directory) {
        directory = directory.substring(1);
        boolean exists = fileExists(directory);

        if (exists & new File(directory).isDirectory()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean directoryExists(String rootDirectory, String directory) {
        String fullDirectoryPath = rootDirectory + directory;
        boolean exists = fileExists(fullDirectoryPath);

        if (exists & new File(fullDirectoryPath).isDirectory()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean fileExists(String directory) {
        File file = new File(directory);
        return file.exists();
    }

    public boolean fileExists(String rootDirectory, String directory) {
        File file = new File(rootDirectory + directory);
        return file.exists();
    }

}
