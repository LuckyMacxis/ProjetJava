package TCP.client;

import Model.company.Company;
import controller.MainFormController;
import form.MainForm;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Client extends TCPClientBuilder implements Runnable {

    private Company company;
    private String file;
    MainFormController mainFormController;
    public Client(String f, MainFormController mainFormController) {
        super();
        file = f;
        this.company = mainFormController.getCompany();
        this.mainFormController = mainFormController;
    }

    public void run() {
        try {
            setSocket();
            //s.setSoTimeout(1000);
            FileOutputStream out = new FileOutputStream(new File(file));
            InputStream f = s.getInputStream();
            byte[] buffer = new byte[8192];
            StringBuffer sBuffer = new StringBuffer(8192);
            if(f!=null)
            {
                do{
                    sBuffer.delete(0, sBuffer.length());
                    f.read(buffer);
                    out.write(buffer);
                    sBuffer.append(buffer);
                    //System.out.println(sBuffer.toString());
                }while(f.read()>0);
            }
            f.close();
            out.close();
            s.close();
            company = Company.deserialize();
            mainFormController.setCompany(company);
            mainFormController.updateComboBox();
        } catch (IOException e) {
            System.out.println("IOException TCPClient : " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
