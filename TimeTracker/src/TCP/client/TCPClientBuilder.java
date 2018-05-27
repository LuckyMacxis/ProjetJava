package TCP.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientBuilder {
    protected Socket s; // the client socket
    protected InetSocketAddress isA; // the remote address

    TCPClientBuilder() {
    }

    void setSocket() throws UnknownHostException, IOException
    {
        isA = new InetSocketAddress("localhost", 8081);
        s = new Socket(isA.getHostName(), isA.getPort());
    }
}
