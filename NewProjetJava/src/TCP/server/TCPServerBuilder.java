package TCP.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerBuilder {
    protected ServerSocket ss;
    protected Socket s; // the passive and active sockets
    protected InetSocketAddress isA; // the address

    /** The main method for threading. */
    TCPServerBuilder() {
    }

    void setSocket() throws IOException{
        s = null;
        isA = new InetSocketAddress("localhost", 8081);
        ss = new ServerSocket(isA.getPort());
    }
}
