package controller;

import Model.Time.Check;
import Model.Time.DateAndTime;
import Model.company.Company;
import Model.company.Employee;
import Model.company.Manager;
import TCP.client.Client;
import TCP.server.Server;
import form.MainForm;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class MainFormController {

    private MainForm theView;
    private Company company;
    private Client client;
    private Server server;
    private volatile ArrayList<Check> listCheck = new ArrayList<>();

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void updateTextFieldParameter(){
        theView.getTextFieldPortServer().setText(Integer.toString(server.getPort()));
        theView.getTextFieldPortClient().setText(Integer.toString(client.getPort()));
        theView.getTextFieldIp().setText(client.getAddress());
    }

    public MainFormController(MainForm mainForm){
        theView = mainForm;

        theView.buttonCheckListener(new ButtonCheckListener());
        theView.buttonSyncListener(new ButtonSyncListener());
        theView.okButtonListener(new OkButtonListener());
        try {
            server = new Server(listCheck);
            server.start();
            client = new Client("save/company.serial", this);
            client.start();
            updateTextFieldParameter();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ButtonCheckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getComboBoxEmployee().getSelectedItem()!=null){
                    String selectedEmployee = theView.getComboBoxEmployee().getSelectedItem().toString();
                    Check check;
                    if (selectedEmployee.split(" ")[0].equals("M")){

                        Manager manager = company.searchManagerWithId(Integer.parseInt(selectedEmployee.split(" ")[1]));
                        DateAndTime dateAndTime = new DateAndTime(LocalDate.now(),LocalTime.now());
                        check = new Check(manager,dateAndTime);
                    }else{
                        Employee employee = company.searchEmployeeWithId(Integer.parseInt(selectedEmployee.split(" ")[0]));
                        DateAndTime dateAndTime = new DateAndTime(LocalDate.now(),LocalTime.now());
                        check = new Check(employee,dateAndTime);
                    }
                    if (check!=null) {
                        listCheck.add(check);
                        System.out.println(listCheck.size());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonSyncListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            synchronized (client) {
                client.notify();
            }
        }
    }

    public void updateComboBox() {
        theView.getComboBoxEmployee().removeAllItems();

                for (Employee e : company.getListEmployees()) {
                    theView.getComboBoxEmployee().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
                }

                for (Manager m : company.getListManagers()) {
                    theView.getComboBoxEmployee().addItem("M "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
                }
                return ;
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                String portServer = theView.getTextFieldPortServer().getText();
                String portClient = theView.getTextFieldPortClient().getText();
                String address = theView.getTextFieldIp().getText();
                Properties config = new Properties();
                config.loadFromXML(new FileInputStream("config/config.xml"));
                config.setProperty("Ip",address);
                config.setProperty("portServer",portServer);
                config.setProperty("portClient",portClient);
                server.setPort(Integer.parseInt(portServer));
                client.setAddressPort(address, Integer.parseInt(portClient));
                config.storeToXML(new FileOutputStream("config/config.xml"),null);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
            updateTextFieldParameter();
        }
    }
}
