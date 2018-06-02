package TCP.server;

import java.io.IOException;

public class Server extends TCPServerBuilder implements Runnable {

    /**
     * The main method for threading.
     */
    public Server() throws IOException {
        super();
    }

    @Override
    public void run() {
        try {
            setSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
            while(true)
            {
                try{
                    //ss.setSoTimeout(1500);
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

