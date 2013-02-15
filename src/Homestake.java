public class Homestake {

    public static void main(String[] args) {
        SocketHandler socketHandler = new SocketHandler(5000);
        HomestakeServer homestakeServer = new HomestakeServer(socketHandler);
        try {
            homestakeServer.startServer();
        }
        catch (Exception exception) {
            System.out.println("Some exception found: " + exception);
        }
    }

    public void startServer(SocketHandler socketHandler) {

    }

}
