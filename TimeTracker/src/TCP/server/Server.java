package TCP.server;

import Model.Time.Check;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server extends TCPServerBuilder implements Runnable {

    volatile ArrayList<Check> listCheck;

    public Server(ArrayList<Check> listCheck) throws IOException {
        super();
        this.listCheck = listCheck;
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
                    TimeUnit.SECONDS.sleep(1);

                    if (listCheck.size() > 0){
                        setSocket();
                        ss.setSoTimeout(500);
                        s = ss.accept();
                        System.out.println("send a check");
                        listCheck.get(0).serialize();
                        listCheck.remove(0);
                        new Thread(new ServerTh(s,"tmp/check.serial")).start();
                    }
                } catch (Exception e) {}

            }
    }
}

