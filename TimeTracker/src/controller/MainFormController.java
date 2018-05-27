package controller;

import Model.Time.Check;
import Model.Time.DateAndTime;
import Model.company.Company;
import Model.company.Employee;
import Model.company.Manager;
import TCP.client.Client;
import TCP.server.Server;
import form.MainForm;
import java.time.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFormController {

    MainForm theView;
    Company company;
    ArrayList<Check> listCheck = new ArrayList<>();

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public MainFormController(MainForm mainForm){
        new Thread(new Server(listCheck)).start();
        theView = mainForm;

        theView.buttonCheckListener(new ButtonCheckListener());
        theView.buttonOptionListener(new ButtonOptionListener());
        theView.buttonSyncListener(new ButtonSyncListener());
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
                    if (check!=null)
                        listCheck.add(check);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonOptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private class ButtonSyncListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new Thread(new Client("save/company.serial",MainFormController.this)).start();
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
}
