package org.homestake;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {
    String mockInput;

    public MockServerSocket(String mockInput) throws IOException {
        this.mockInput = mockInput;
    }

    @Override
    public Socket accept() throws IOException {
        return new MockSocket(mockInput);
    }

}