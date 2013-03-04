package org.homestake.utils;

import org.homestake.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWrapper {
    public int port;
    private boolean closed = true;
    public ServerSocket serverSocket;
    public Logger logger = new Logger();

    public SocketWrapper(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(java.io.IOException exception) {
            logger.printMessage("Socket initialization failed: "  + exception.getMessage() );
        }
    }

    public SocketWrapper(int port, Logger logger) {
        this.port = port;
        this.logger = logger;
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(java.io.IOException exception) {
            logger.printMessage("Socket initialization failed: "  + exception.getMessage() );
        }
    }

    public SocketWrapper(int port, Logger logger, ServerSocket serverSocket) {
        this.port = port;
        this.serverSocket = serverSocket;
        this.logger = logger;
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
            logger.printMessage("Socket accept failed: " + exception.getMessage());
        }
        return returnSocket;
    }

    public void close() {
        try {
            this.serverSocket.close();
        }
        catch(IOException exception) {
            logger.printMessage("Socket close failed: " + exception.getMessage());
        }
    }

    public int getLocalPort() {
        return this.port;
    }

}
