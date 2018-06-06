package TCP.client;

import Model.Time.Check;
import Model.company.Company;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Client extends TCPClientBuilder implements Runnable {

    private String file;
    private Company company;
    private boolean status = false;

    /**
     * Construct a new TCP Client
     * @param f String the file where to save the received checks
     * @param company Company, the model
     * @throws IOException If the connection with the server of the Time Tracker emulator is impossible
     */
    public Client(String f,Company company) throws IOException {
        super();
        file = f;
        this.company = company;
    }

    /**
     * the client is always waiting for incoming checks, if it lost the connection with the server of the time Tracker
     * he will try to reconnect until it did it
     */
    public void run() {
           while (true){
               try {
                   status = false;
                   while (!status)
                       connection();
                   System.out.println("\n Client online");
                   FileOutputStream out = new FileOutputStream(new File(file));
                   InputStream f = s.getInputStream();
                   byte[] buffer = new byte[8192];
                   if(f!=null)
                   {
                       do{
                           f.read(buffer);
                           out.write(buffer);
                       }while(f.read()>0);
                       Check check = Check.deserialize();
                       if (company.isEmployeeIn(check.getEmployee())){
                           company.addCheckToEmployee(company.searchEmployeeWithId(check.getEmployee().getId()),check);

                       }else {
                           company.addCheckToManager(company.searchManagerWithId(check.getEmployee().getId()),check);
                       }
                   }

                   f.close();
                   out.close();
                   s.close();
                   status = false;
               } catch (IOException e) {
                    System.out.println("IOException TCPClient : " + e.getMessage());
                    status = false;
               }catch (Exception e){
                    e.printStackTrace();
                    status = false;
                }
           }

    }

    /**
     * method which try to connect the client to the time tracker server
     */
    public void connection(){
        status = false;
        try{
            setSocket();
            status = true;
        }catch (IOException e){
        }
    }
}
