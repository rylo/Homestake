package org.homestake;

import org.homestake.utils.*;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPOutputStream;

public class Homestake {
    public static int port = 5000;
    public static String rootDirectory = "public";
    private Router router = new Router(rootDirectory);
    private ExecutorService threadPool = Executors.newFixedThreadPool(75, new HomestakeThreadFactory());
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
            if (arg.equals("-d") || arg.equals("-directory")) { rootDirectory = args[index + 1]; }
            index ++;
        }
    }

    public void startServer() throws IOException {

        while(true) {
            final Socket clientConnection = socket.accept();

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Long threadID = Thread.currentThread().getId();
                        Logger.destroyQueue(threadID);
                        Date startTime = new Date();

                        BufferedReader clientInputStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                        String requestString = clientInputStream.readLine();

                        // Handle phantom null requests
                        if ( requestString != null ) {
                            RequestParser request = new RequestParser(requestString);
                            sendResponses(clientConnection, getServerResponse(request));

                            Logger.addToQueue(threadID,
                                    "Started - " + request.method + " \"" + request.rawRoute + "\"" +
                                            " for " + clientConnection.getInetAddress().toString().replace("/", "") +
                                            " at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZ").format(startTime) + "\n" +
                                    "Finished in " + (new Date().getTime() - startTime.getTime()) + "ms");
                            Logger.writeQueuedMessages(threadID);
                        }

                        clientConnection.close();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }

    }

    public Map<String, InputStream> getServerResponse(RequestParser request) throws Exception {
        if (request != null) {
            return router.routeRequest(request);
        }
        else {
            return null;
        }
    }

    public void sendResponses(Socket server, Map<String, InputStream> response) throws IOException {
        OutputStream outputStream = null;
        for(Map.Entry<String, InputStream> entry : response.entrySet()) {
            outputStream = setCompression(entry.getKey(), server.getOutputStream());
            writeResponseToSocket(server, outputStream, entry.getValue());
            try {
                outputStream.flush();
            }
            catch (Exception exception) {
                break;
            }
        }
        outputStream.close();
    }

    public void writeResponseToSocket(Socket server, OutputStream out, InputStream in) throws IOException {
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

    public OutputStream setCompression(String compressionType, OutputStream serverOutputStream) throws IOException {
        if (compressionType.contains("gzip")) {
            return new BufferedOutputStream(new GZIPOutputStream(serverOutputStream));
        }
        else {
            return new BufferedOutputStream(serverOutputStream);
        }
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