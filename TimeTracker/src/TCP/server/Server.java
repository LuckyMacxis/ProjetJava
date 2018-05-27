package TCP.server;

import Model.Time.Check;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server extends TCPServerBuilder implements Runnable {

    ArrayList<Check> listCheck = new ArrayList<>();

    public Server(ArrayList<Check> listCheck) {
        super();
        this.listCheck = listCheck;
    }

    @Override
    public void run() {
        try{
            setSocket();
            System.out.println("\n Server online");
            //ssInfo("The server sets the passive socket", ss);
            while(true)
            {
                TimeUnit.SECONDS.sleep(1);
                if (listCheck.size() > 0){
                    s = ss.accept();
                    System.out.println("send a check");
                    listCheck.get(0).serialize();
                    listCheck.remove(0);
                    new Thread(new ServerTh(s,"tmp/check.serial")).start();
                }

            }

        }	catch(IOException e){
            System.out.println("Server file :" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

