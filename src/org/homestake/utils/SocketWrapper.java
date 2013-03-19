package org.homestake.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWrapper {
    public int port;
    private boolean closed = true;
    public ServerSocket serverSocket;

    public SocketWrapper(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(java.io.IOException exception) {
            System.out.println("Socket initialization failed: " + exception.getMessage());
        }
    }

    public SocketWrapper(int port, ServerSocket serverSocket) {
        this.port = port;
        this.serverSocket = serverSocket;
    }

    public boolean isClosed () {
        return closed;
    }

    public Socket accept() {
        Socket returnSocket = null;
        try {
            returnSocket = serverSocket.accept();
            closed = false;
        }
        catch(IOException exception) {
            close();
            System.out.println("Socket accept failed: " + exception.getMessage());
        }
        return returnSocket;
    }

    public void close() {
        try {
            this.serverSocket.close();
        }
        catch(IOException exception) {
            System.out.println("Socket close failed: " + exception.getMessage());
        }
    }

    public int getLocalPort() {
        return this.port;
    }

}
