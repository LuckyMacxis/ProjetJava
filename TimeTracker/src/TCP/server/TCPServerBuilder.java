package TCP.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class TCPServerBuilder extends Thread{
    protected volatile ServerSocket ss;
    protected volatile Socket s; // the passive and active sockets
    protected volatile InetSocketAddress isA; // the address
    private volatile int port = 8080;

    /** The main method for threading. */
    TCPServerBuilder() throws IOException {
        Properties config = new Properties();
        config.loadFromXML(new FileInputStream("config/config.xml"));
        port = Integer.parseInt(config.getProperty("portServer"));
    }

    void setSocket() throws IOException{
        s = null;
        isA = new InetSocketAddress("localhost", port);
        if (ss!=null)
            ss.close();
        ss = new ServerSocket(isA.getPort());
    }

    public void setPort(int port) throws IOException {
        this.port = port;
        if (ss!=null)
            ss.close();
        ss = new ServerSocket(port);
    }

    public int getPort() {
        return port;
    }
}
