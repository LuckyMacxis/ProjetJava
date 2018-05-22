package Controller;

import Form.FormAddDepartment;
import Model.company.Company;
import Model.company.Department;
import Model.company.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDepartmentController {

    private FormAddDepartment theView;

    private Company company;

    private Department department;

    public AddDepartmentController(FormAddDepartment theView, Company company) {
        this.theView = theView;
        this.company = company;

        theView.addButtonOkListener(new ButtonOkListener());
        theView.addButtonCancelListener(new ButtonCancelListener());
        try {
            fillComboBox();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void fillComboBox() throws Exception {
        for (Manager m : company.getListManagers()){
            if (!company.isInADepartment(m)) {
                theView.getComboBox1().addItem(m.getId()+" "+m.getLastname()+" "+m.getFirstname());
            }
        }
    }

    private class ButtonOkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getComboBox1().getItemCount() < 1)
                    throw new Exception("You have to create a new manager");
                String sId = (String)theView.getComboBox1().getSelectedItem();
                int id = Integer.parseInt(sId.split(" ")[0]);
                Manager manager = company.searchManagerWithId(id);
                department = new Department(theView.getTextField1().getText(),manager);
                company.addDepartment(department);
                theView.dispose();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private class ButtonCancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            department = null;
            theView.dispose();
        }
    }
}
