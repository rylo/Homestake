import java.io.*;
import java.net.*;

public class MockSocket extends Socket {
    private boolean closed = false;
    InputStream input;
    OutputStream output;

    public MockSocket(String mockInput) {
        this.input = new ByteArrayInputStream(mockInput.getBytes());
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
