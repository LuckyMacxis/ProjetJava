package TCP.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class ServerTh implements Runnable {

    private Socket s; // the passive and active sockets
    private String file;
    public ServerTh(Socket sok, String f)
    {
        s = sok;
        file = f;
    }

    @Override
    public void run() {
        try{
            if(s!=null)
            {
                FileInputStream f = new FileInputStream(file);
                OutputStream in = s.getOutputStream();
                int count;
                byte[] buffer = new byte[8192];
                do{
                    count = f.read(buffer);
                    in.flush();
                    in.write(buffer);
                    in.flush();
                }while(count>0);
                in.close();
                f.close();
                s.close();
            }

        }	catch(IOException e){
            System.out.println("Serveur file :" + e.getMessage());
        }
    }
}