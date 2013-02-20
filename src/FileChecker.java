import java.io.File;

public class FileChecker {
    public String rootDirectory;

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

}
