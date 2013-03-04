package org.homestake;

import org.homestake.utils.Router;
import org.homestake.utils.SocketWrapper;

import java.io.*;
import java.net.Socket;

public class Homestake {
    private SocketWrapper socketWrapper;

    public Homestake() {
        this.socketWrapper = new SocketWrapper(5000);
    }

    public Homestake(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public static void main(String[] args) {
        Homestake homestakeServer = new Homestake();
        try {
            homestakeServer.startServer();
        }
        catch (Exception exception) {
            System.out.println("Server stopped. Exception found: " + exception);
            System.out.println("Stack trace: " + exception.getStackTrace().toString());
        }
    }

    public void startServer() throws IOException {
        Socket server = socketWrapper.accept();

        while(!server.isClosed()) {
            sendResponse(server, getServerResponse(server));
            server.close();
            server = socketWrapper.accept();
        }
    }

    public InputStream getServerResponse(Socket server) throws IOException {
        BufferedReader clientRequest = new BufferedReader(new InputStreamReader(server.getInputStream()));
        return new Router().routeRequest(clientRequest.readLine());
    }

    public void sendResponse(Socket server, InputStream response) throws IOException {
        OutputStream out = server.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(response);
        int line;

        while((line = bufferedInputStream.read()) != -1 && !server.isClosed()) {
            try {
                bufferedOutputStream.write(line);
            }
            catch(Exception exception) {
                break;
            }
        }
        bufferedOutputStream.close();
    }

}