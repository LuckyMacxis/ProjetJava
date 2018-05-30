package TCP.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientBuilder extends Thread{
    protected volatile Socket s; // the client socket
    protected volatile InetSocketAddress isA; // the remote address
    private volatile String address = "localhost";
    private volatile int port = 8080;

    TCPClientBuilder() {
    }

    void setSocket() throws UnknownHostException, IOException {
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
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getPort() {
        return port;
    }
}
