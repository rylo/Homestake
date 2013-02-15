import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {
    public ServerSocket serverSocket;
    public Logger logger = new Logger();

    public SocketHandler(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(java.io.IOException exception) {
            logger.printMessage("Socket initialization failed: "  + exception.getMessage() );
        }
    }

    public SocketHandler(int port, Logger logger) {
        this.logger = logger;
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(java.io.IOException exception) {
            logger.printMessage("Socket initialization failed: "  + exception.getMessage() );
        }
    }

    public SocketHandler(int port, Logger logger, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.logger = logger;
    }

    public Socket accept() {
        Socket returnSocket = null;
        try {
            returnSocket = serverSocket.accept();
        }
        catch(IOException exception) {
            close();
            logger.printMessage("Socket accept failed: " + exception.getMessage());
        }
        logger.printMessage("Socket accepted connection");
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

    public void getOutputStream() {
        try {
            this.serverSocket.getReceiveBufferSize();
        }
        catch(IOException exception) {
            logger.printMessage(exception.getMessage());
        }
    }

}
