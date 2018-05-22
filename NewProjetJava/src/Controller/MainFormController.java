package Controller;

import Form.*;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFormController {
    private MainForm theView;

    private Company company;

    public class ModelTableStaff extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    }

    public MainFormController(MainForm mainForm, Company company) {
        this.theView = mainForm;
        this.company = company;

        theView.addStaffListener(new StaffListener());
        theView.addDepartmentListener(new DepartmentListener());
        theView.addComboBoxDepartmentListener(new ComboBoxDepartmentListener());
        theView.employeeCheckHistoryListener(new CheckHistoryListener());
        theView.addButtonDeleteFromCompanyListener(new ButtonDeleteFromCompanyListener());
        theView.addButtonDeleteFromDepartmentListener(new ButtonDeleteFromDepartmentListener());
        theView.assignStaffListener(new AssignStaffListener());
        theView.buttonDeleteDepartmentListener(new ButtonDeleteDepartmentListener());
        theView.radioButtonListener(new RadioButtonListener());
        theView.buttonSetAsChiefListener(new ButtonSetAsChiefListener());

        theView.getTableStaff().setRowSelectionAllowed(true);
        theView.getTableStaff().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        theView.getTableStaff().getTableHeader().setReorderingAllowed(false);

        updateComboBoxDepartment();
        try {
            updateTableStaff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateComboBoxDepartment(){
        theView.getComboBoxDepartment().removeAllItems();
        theView.getComboBoxDepartment().addItem("All");
        theView.getComboBoxDepartment().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartment().addItem(d.getName());
        }
    }

    private void updateTableStaff() throws Exception {

        ModelTableStaff modelTableStaff = new ModelTableStaff();

        modelTableStaff.addColumn("Id");
        modelTableStaff.addColumn("First name");
        modelTableStaff.addColumn("Surname");
        modelTableStaff.addColumn("Function");
        modelTableStaff.addColumn("Arriving Time");
        modelTableStaff.addColumn("Departure Time");
        modelTableStaff.addColumn("Additional Time");

        if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("All")){
            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : company.getListManagers()) {
                    if (m.isChief()) {
                        modelTableStaff.addRow(new Object[]{
                                m.getId(),
                                m.getFirstname(),
                                m.getLastname(),
                                "Chief",
                                m.getArrivingTime().toString(),
                                m.getDepartureTime().toString(),
                                m.getAdditionalTime()
                        });
                    } else {
                        modelTableStaff.addRow(new Object[]{
                                m.getId(),
                                m.getFirstname(),
                                m.getLastname(),
                                "Manager",
                                m.getArrivingTime().toString(),
                                m.getDepartureTime().toString(),
                                m.getAdditionalTime()
                        });
                    }
                }
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : company.getListEmployees()) {
                    modelTableStaff.addRow(new Object[]{
                            e.getId(),
                            e.getFirstname(),
                            e.getLastname(),
                            "Employee",
                            e.getArrivingTime().toString(),
                            e.getDepartureTime().toString(),
                            e.getAdditionalTime()
                    });
                }
            }
        }

        if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("None")){

            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : company.getListManagers()) {
                    if (!company.isInADepartment(m))
                        modelTableStaff.addRow(new Object[]{
                                m.getId(),
                                m.getFirstname(),
                                m.getLastname(),
                                "Manager",
                                m.getArrivingTime().toString(),
                                m.getDepartureTime().toString(),
                                m.getAdditionalTime()
                        });
                    }
                }

            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : company.getListEmployees()) {
                    if (!company.isInADepartment(e)) {
                        modelTableStaff.addRow(new Object[]{
                                e.getId(),
                                e.getFirstname(),
                                e.getLastname(),
                                "Employee",
                                e.getArrivingTime().toString(),
                                e.getDepartureTime().toString(),
                                e.getAdditionalTime()
                        });
                    }
                }
            }
        }

        if (!theView.getComboBoxDepartment().getSelectedItem().toString().equals("None") && !theView.getComboBoxDepartment().getSelectedItem().toString().equals("All")){
            Department department = company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString());
            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : department.getListManagers()) {
                    if (m.isChief()) {
                        modelTableStaff.addRow(new Object[]{
                                m.getId(),
                                m.getFirstname(),
                                m.getLastname(),
                                "Chief",
                                m.getArrivingTime().toString(),
                                m.getDepartureTime().toString(),
                                m.getAdditionalTime()
                        });
                    } else {
                        modelTableStaff.addRow(new Object[]{
                                m.getId(),
                                m.getFirstname(),
                                m.getLastname(),
                                "Manager",
                                m.getArrivingTime().toString(),
                                m.getDepartureTime().toString(),
                                m.getAdditionalTime()
                        });
                    }
                }
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : department.getListEmployees()) {
                    modelTableStaff.addRow(new Object[]{
                            e.getId(),
                            e.getFirstname(),
                            e.getLastname(),
                            "Employee",
                            e.getArrivingTime().toString(),
                            e.getDepartureTime().toString(),
                            e.getAdditionalTime()
                    });
                }
            }
        }
        theView.getTableStaff().setModel(modelTableStaff);

    }


    //<editor-fold desc = "Class">


    private class ComboBoxDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (theView.getComboBoxDepartment().getSelectedItem() != null)
            {
                try {
                    updateTableStaff();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class StaffListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormAddStaff f = new FormAddStaff(theView,"test",company.getListDepartment());
            AddStaffController addStaffControl = new AddStaffController(f,company);
            f.setVisible(true);
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class DepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormAddDepartment f = new FormAddDepartment(theView,"Add department");
            AddDepartmentController addDepControl = new AddDepartmentController(f,company);
            f.setVisible(true);
            updateComboBoxDepartment();
        }
    }

    private class CheckHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormCheckHistory formCheckHistory = new FormCheckHistory(theView,"Checks history");
            ChecksHistoryController checksHistoryController = new ChecksHistoryController(formCheckHistory,company);
            formCheckHistory.setVisible(true);
        }
    }

    private class ButtonDeleteFromCompanyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int nbRow;
            try {
                nbRow =theView.getTableStaff().getSelectedRow();
                if (nbRow == -1)
                    return;
                int id = Integer.parseInt(theView.getTableStaff().getValueAt(nbRow,0).toString());
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Employee")){
                    company.removeEmployee(company.searchEmployeeWithId(id));
                }

                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Manager")){
                    company.removeManager(company.searchManagerWithId(id));
                }

                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Chief")){
                    JOptionPane.showMessageDialog(null,"This manager is the chief of a department, he can't be removed","Error",JOptionPane.INFORMATION_MESSAGE);
                }
                updateTableStaff();
                //TODO : serialize
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private class ButtonDeleteFromDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int nbRow;
            try {
                if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("All") || theView.getComboBoxDepartment().getSelectedItem().toString().equals("None"))
                    throw new Exception("Please select a department");
                nbRow =theView.getTableStaff().getSelectedRow();
                if (nbRow == -1)
                    return;
                Department department = company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString());
                int id = Integer.parseInt(theView.getTableStaff().getValueAt(nbRow,0).toString());
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Employee")){
                    company.removeEmployeeFromDepartment(company.searchEmployeeWithId(id),department);
                }

                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Manager")){
                    company.removeManagerFromDepartment(company.searchManagerWithId(id),department);
                }

                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Chief")){
                    JOptionPane.showMessageDialog(null,"This manager is the chief of a department, he can't be removed","Error",JOptionPane.INFORMATION_MESSAGE);
                }
                updateTableStaff();
                //TODO : serialize
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AssignStaffListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormAssignStaff formAssignStaff = new FormAssignStaff(theView,"Assign Staff");
            try {
                AssignStaffController assignStaffController = new AssignStaffController(formAssignStaff,company);
            } catch (Exception e) {
                e.printStackTrace();
            }
            formAssignStaff.setVisible(true);
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonDeleteDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("All") || theView.getComboBoxDepartment().getSelectedItem().toString().equals("None"))
                    throw new Exception("Please select a department");
                company.removeDepartment(company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString()));
                updateComboBoxDepartment();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class RadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonSetAsChiefListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int nbRow = theView.getTableStaff().getSelectedRow();
            int id = Integer.parseInt(theView.getTableStaff().getValueAt(nbRow,0).toString());
            try {
                if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("All") || theView.getComboBoxDepartment().getSelectedItem().toString().equals("None"))
                    throw new Exception("Please select a department");
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Chief"))
                    throw new Exception("He is already the chief of this department");
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Employee"))
                    throw new Exception("An employee can not be the chief of a department");
                Department department = company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString());
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Manager"))
                    company.changeChiefOfDepartment(department,company.searchManagerWithId(id));
                updateTableStaff();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //</editor-fold>

}
