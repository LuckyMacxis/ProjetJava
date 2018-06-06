package TCP.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class TCPClientBuilder extends Thread{
    protected volatile Socket s; // the client socket
    protected volatile InetSocketAddress isA; // the remote address
    private volatile String address;
    private volatile int port;

    /**
     * Construct a TCP Client
     * @throws IOException if the Client can't make a connection with the time tracker server
     */
    TCPClientBuilder() throws IOException {
        Properties config = new Properties();
        config.loadFromXML(new FileInputStream("config/config.xml"));
        address = config.getProperty("Ip");
        port = Integer.parseInt(config.getProperty("portClient"));
    }

    /**
     * Create the socket
     * @throws IOException if the socket can't reach the time tracker server
     */
    void setSocket() throws IOException {
        isA = new InetSocketAddress(address, port);
        s = new Socket(isA.getHostName(), isA.getPort());
    }

    public String getAddress() {
        return address;
    }

    public void setAddressPort(String address,int port) {
        this.address = address;
        this.port = port;
        try {
            if (s!=null)
                s.close();
            setSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
}
