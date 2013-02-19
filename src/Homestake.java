import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Homestake {
    private SocketWrapper socketWrapper = new SocketWrapper(5000);

    public static void main(String[] args) {
        Homestake homestakeServer = new Homestake();
        try {
            homestakeServer.startServer();
        }
        catch (Exception exception) {
            System.out.println("Some exception found: " + exception);
        }
    }

    public void startServer() throws IOException {
        Socket server = socketWrapper.accept();

        while(!server.isClosed()) {
            scanInput(server, server.getInputStream());
            server = socketWrapper.accept();
        }
    }

    public String scanInput(Socket server, InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        String string;
        while ((string = in.readLine()) != null) {
            System.out.println(string);
            if (string.isEmpty()) {
                break;
            }
        }
        Scanner scanner = new Scanner(inputStream);
        return scanner.hasNext() ? scanner.next() : "";
    }

}
