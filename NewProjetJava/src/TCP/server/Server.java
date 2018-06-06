package TCP.server;

import java.io.IOException;

public class Server extends TCPServerBuilder implements Runnable {


    public Server() throws IOException {
        super();
    }


    /**
     * the server wait for a connection with the client of the time tracker,
     * when a client is connected this server server create a new server which will send the company
     */
    public void run() {
        try {
            setSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
            while(true)
            {
                try{
                    s = ss.accept();
                    if(s!=null)
                    {
                        System.out.println("connected");
                        new Thread(new ServerTh(s,"save/company.serial")).start();
                    }
                }catch(IOException e){
                    System.out.println("Server file :" + e.getMessage());
                }
            }


    }
}

