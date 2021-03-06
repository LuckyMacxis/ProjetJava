package TCP.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class TCPClientBuilder extends Thread{
    protected Socket s; // the client socket
    private String address;
    private int port;

    TCPClientBuilder() throws IOException {
        Properties config = new Properties();
        config.loadFromXML(new FileInputStream("config/config.xml"));
        address = config.getProperty("Ip");
        port = Integer.parseInt(config.getProperty("portClient"));
    }

    void setSocket() throws IOException {
        // the remote address
        InetSocketAddress isA = new InetSocketAddress(address, port);
        s = new Socket(isA.getHostName(), isA.getPort());
    }

    public void setAddressPort(String address,int port) {
        this.address = address;
        this.port = port;
        try {
            if (s != null)
                s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
