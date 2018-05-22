package Controller;

import Form.FormAssignStaff;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignStaffController {

    private FormAssignStaff theView;

    private Company company;

    public AssignStaffController(FormAssignStaff theView, Company company) throws Exception {
        this.theView = theView;
        this.company = company;

        updateComboBoxDepartment();
        updateComboBoxStaff();

        theView.assignButtonListener(new AssignButtonListener());
        theView.cancelButtonListener(new CancelButtonListener());

    }

    public void updateComboBoxDepartment(){
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartment().addItem(d.getName());
        }
    }

    public void updateComboBoxStaff() throws Exception {
        for (Manager m : company.getListManagers()) {
            if (!company.isInADepartment(m))
                theView.getComboBoxStaff().addItem("M"+" "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
        }

        for (Employee e : company.getListEmployees()) {
            if (!company.isInADepartment(e))
                theView.getComboBoxStaff().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
        }
    }


    //<editor-fold desc = "class">

    private class AssignButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (theView.getComboBoxStaff().getSelectedItem().toString().split(" ")[0].equals("M")){
                try {
                    company.addManagerToDepartment(company.searchManagerWithId(Integer.parseInt(theView.getComboBoxStaff().getSelectedItem().toString().split(" ")[1])) ,
                            company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    company.addEmployeeToDepartment(company.searchEmployeeWithId(Integer.parseInt(theView.getComboBoxStaff().getSelectedItem().toString().split(" ")[0])) ,
                            company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            theView.dispose();
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            theView.dispose();
        }
    }

    //</editor-fold>
}
