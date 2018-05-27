package TCP.server;

import java.io.IOException;

public class Server extends TCPServerBuilder implements Runnable {

    @Override
    public void run() {
        try{
            setSocket();
            //ssInfo("The server sets the passive socket", ss);
            while(true)
            {
                s = ss.accept();
                if(s!=null)
                {
                    System.out.println("conected");
                    new Thread(new ServerTh(s,"save/company.serial")).start();
                }
            }

        }	catch(IOException e){
            System.out.println("Serveur file :" + e.getMessage());
        }
    }
}

