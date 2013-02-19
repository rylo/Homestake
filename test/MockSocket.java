import java.io.*;
import java.net.*;

public class MockSocket extends Socket {
    private boolean closed = false;
    InputStream input;
    OutputStream output;

    public MockSocket(String input) {
        this.input = new ByteArrayInputStream(input.getBytes());
        output = new ByteArrayOutputStream();
    }

    public boolean isClosed() {
        return closed;
    }

    public OutputStream getOutputStream() {
        return output;
    }

    public InputStream getInputStream() {
        return input;
    }

    public void close() {
        this.closed = true;
    }

}
