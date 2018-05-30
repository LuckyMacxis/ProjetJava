package TCP.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerBuilder extends Thread{
    protected volatile   ServerSocket ss;
    protected volatile Socket s; // the passive and active sockets
    protected volatile InetSocketAddress isA; // the address
    private volatile int port = 8081;

    /** The main method for threading. */
    TCPServerBuilder() {
    }

    public void setSocket() throws IOException{
        s = null;
        isA = new InetSocketAddress("localhost", port);
        ss = new ServerSocket(isA.getPort());
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) throws IOException {
        this.port = port;
        if (ss!=null)
            ss.close();
        ss = new ServerSocket(port);
    }
}
