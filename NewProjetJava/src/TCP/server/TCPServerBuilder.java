package TCP.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class TCPServerBuilder extends Thread{
    protected volatile   ServerSocket ss;
    protected volatile Socket s;
    protected volatile InetSocketAddress isA; // the address
    private volatile int port = 8081;

    /**
     * Construct the base of a TCP server
     * @throws IOException if the config file can't be read
     */
    TCPServerBuilder() throws IOException {
        Properties config = new Properties();
        config.loadFromXML(new FileInputStream("config/config.xml"));
        port = Integer.parseInt(config.getProperty("portServer"));
    }

    /**
     * set the passive socket of the server
     * @throws IOException -
     */
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
