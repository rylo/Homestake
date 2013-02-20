import java.io.*;
import java.net.Socket;

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
            InputStream response = getResponse(server);
            sendResponse(server, response);
            server.close();
            server = socketWrapper.accept();
        }
    }

    public InputStream getResponse(Socket server) throws IOException {
        BufferedReader clientRequest = new BufferedReader(new InputStreamReader(server.getInputStream()), 10000);
        return new Router().routeRequest(clientRequest.readLine());
    }

    public void sendResponse(Socket server, InputStream response) throws IOException {
        OutputStream out = server.getOutputStream();
        int line;

        while((line = response.read()) != -1) {
            out.write(line);
        }
        out.flush();
    }

}