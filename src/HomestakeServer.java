import java.io.*;
import java.net.Socket;

public class HomestakeServer {
    private SocketHandler socketHandler;

    public HomestakeServer(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void startServer() throws Exception {
        System.out.println("Starting server...");

        while(true) {
            Socket client = socketHandler.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n"); // The content starts afters this empty line
            out.write("<TITLE>Hello!</TITLE>");
            out.write("<h1>O HI DERE LOL</h1>");

            out.flush();
            out.close();
            in.close();
        }
    }
}
