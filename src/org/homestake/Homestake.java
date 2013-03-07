package org.homestake;

import org.homestake.utils.Router;
import org.homestake.utils.SocketWrapper;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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

    public HashMap<String, InputStream> getServerResponse(Socket server) throws IOException {
        BufferedReader clientRequest = new BufferedReader(new InputStreamReader(server.getInputStream()));
        return new Router(rootDirectory).routeRequest(clientRequest.readLine());
    }

    public void sendResponse(Socket server, HashMap<String, InputStream> response) throws IOException {
        BufferedOutputStream out = prepareOutputStream(server.getOutputStream());
        for(Map.Entry<String, InputStream> entry : response.entrySet()) {
            writeResponse(server, out, entry.getValue());
        }
        out.close();
    }

    public void writeResponse(Socket server, BufferedOutputStream out, InputStream in) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        int line;
        while((line = bufferedInputStream.read()) != -1 && !server.isClosed()) {
            try {
                out.write(line);
            }
            catch(Exception exception) {
                break;
            }
        }
    }

    public BufferedOutputStream prepareOutputStream(OutputStream serverOutputStream) {
        return new BufferedOutputStream(serverOutputStream);
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