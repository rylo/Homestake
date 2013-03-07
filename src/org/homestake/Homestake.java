package org.homestake;

import org.homestake.utils.Router;
import org.homestake.utils.SocketWrapper;
import java.io.*;
import java.net.Socket;
import java.util.Set;

public class Homestake {
    public static int port = 5000;
    public static String rootDirectory = "public";
    private SocketWrapper socket;

    public Homestake() {
        this.socket = new SocketWrapper(port);
    }

    public Homestake(SocketWrapper socketWrapper) {
        this.socket = socketWrapper;
    }

    public static void main(String[] args) {
        parseArgs(args);
        Homestake homestakeServer = new Homestake();

        try {
            homestakeServer.startServer();
        }
        catch (Exception exception) {
            System.out.println("Server stopped. Exception found: " + exception);
            System.out.println("Stack trace: ");
                exception.printStackTrace();
        }
    }

    public static void parseArgs(String[] args) {
        int index = 0;
        for(String arg : args) {
            if (arg.equals("-p") || arg.equals("-port")) { port = Integer.parseInt(args[index + 1]); }
            if (arg.equals("-root")) { rootDirectory = args[index + 1]; }
            index ++;
        }
    }

    public void startServer() throws IOException {

        while(true) {
            final Socket clientConnection = socket.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendResponse(clientConnection, getServerResponse(clientConnection));
                        clientConnection.close();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }, "homestake-response-thread").start();
        }

    }

    public InputStream getServerResponse(Socket server) throws IOException {
        BufferedReader clientRequest = new BufferedReader(new InputStreamReader(server.getInputStream()));
        return new Router(rootDirectory).routeRequest(clientRequest.readLine());
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

    public Set getThreads() {
        return Thread.getAllStackTraces().keySet();
    }

    public int getThreadCount(String threadName) {
        Set threads = getThreads();
        int count = 0;

        for(Object thread : threads) {
            if ( thread.toString().contains("Thread[" + threadName) ){
                count ++;
            }
        }
        return count;
    }

}