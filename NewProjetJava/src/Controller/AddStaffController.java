package Controller;

import Form.FormAddStaff;
import Model.Time.DateAndTime;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStaffController {

    private FormAddStaff theView;
    private Company company;

    private Employee employee;
    private Manager manager;

    public AddStaffController(FormAddStaff theView, Company company) {
        this.theView = theView;
        this.company = company;
        theView.addButtonOkListener(new ButtonOkListener());
        theView.addButtonCancelListener(new ButtonCancelListener());
    }

    private class ButtonOkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String mail;
            if (theView.getTextFieldMail().getText().equals("") || theView.getTextFieldMail().getText() == null){
                mail = theView.getTextFieldMail().getText();
            }else{
                mail = " ";
            }
            if(theView.getManagerCheckBox().isSelected()){
                try {

                    manager = new Manager(theView.getTextFieldLastName().getText(),
                            theView.getTextFieldFirstName().getText(),
                            mail,
                            new DateAndTime(theView.getTextFieldArriving().getText(),DateAndTime.TIME),
                            new DateAndTime(theView.getTextFieldDeparture().getText(), DateAndTime.TIME));
                    company.addManager(manager);
                    if (!theView.getComboBox1().getSelectedItem().equals("None")){
                        for (Department d : company.getListDepartment()) {
                            if (d.getName().equals(theView.getComboBox1().getSelectedItem())){
                                d.addManager(manager);
                            }
                        }
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Please enter valid text.","Error",JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                try {
                    employee = new Employee(theView.getTextFieldLastName().getText(),
                            theView.getTextFieldFirstName().getText(),
                            mail,
                            new DateAndTime(theView.getTextFieldArriving().getText(),DateAndTime.TIME),
                            new DateAndTime(theView.getTextFieldDeparture().getText(), DateAndTime.TIME));
                    company.addEmployee(employee);
                    if (!theView.getComboBox1().getSelectedItem().equals("None")){
                        for (Department d : company.getListDepartment()) {
                            if (d.getName().equals(theView.getComboBox1().getSelectedItem())){
                                d.addEmployee(employee);
                            }
                        }
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Please enter valid text.","Error",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            theView.dispose();
        }
    }

    private class ButtonCancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            employee = null;
            manager = null;
            theView.dispose();
        }
    }
}
