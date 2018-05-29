package Controller;

import Form.*;
import Model.Time.Check;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class MainFormController {
    private MainForm theView;

    private Company company;

    public class MyModelTable extends DefaultTableModel{
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
        theView.buttonAddDepartmentListener(new ButtonAddDepartmentListener());
        theView.textFieldSearchDepartmentListener(new TextFieldSearchDepartmentListener());
        theView.comboBoxDepartmentCheckListener(new ComboBoxDepartmentCheckListener());
        theView.comboBoxEmployeeCheckListener(new ComboBoxEmployeeCheckListener());
        theView.tabbedPaneListener(new tabbedPaneListener());
        theView.importButtonListener(new ImportButtonListener());
        theView.exportButtonListener(new ExportButtonListener());

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

    //<editor-fold desc = "Update Method">

    private void updateComboBoxDepartment(){
        theView.getComboBoxDepartment().removeAllItems();
        theView.getComboBoxDepartment().addItem("All");
        theView.getComboBoxDepartment().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartment().addItem(d.getName());
        }
    }

    private void updateTableStaff() throws Exception {

        MyModelTable modelTableStaff = new MyModelTable();

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

    private void updateComboBoxChief(){
        try {
            theView.getComboBoxChief().removeAllItems();
            for (Manager m : company.getListManagers()){
                if (!company.isInADepartment(m)) {
                    theView.getComboBoxChief().addItem(m.getId()+" "+m.getLastname()+" "+m.getFirstname());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void updateTableDepartment(){
        MyModelTable modelTableDepartment = new MyModelTable();

        modelTableDepartment.addColumn("Name");
        modelTableDepartment.addColumn("Chief");
        modelTableDepartment.addColumn("Number of employee");
        modelTableDepartment.addColumn("Number of manager");

        for (Department d : company.getListDepartment()) {
            Manager manager = null;
            for (Manager m : d.getListManagers()) {
                if (m.isChief()){
                    manager = m;
                    break;
                }
            }
            if (manager == null)
                break;
            if (theView.getTextFieldSearchDepartment().getText().equals("") || d.getName().contains(theView.getTextFieldSearchDepartment().getText())) {
                modelTableDepartment.addRow(new Object[]{
                        d.getName(),
                        manager.getFirstname(),
                        d.getListEmployees().size(),
                        d.getListManagers().size()
                });
            }
        }
        theView.getTableDepartment().setModel(modelTableDepartment);

    }

    private void updateComboBoxDepartmentCheck(){
        theView.getComboBoxDepartmentCheck().removeAllItems();
        theView.getComboBoxDepartmentCheck().addItem("All");
        theView.getComboBoxDepartmentCheck().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartmentCheck().addItem(d.getName());
        }
    }

    private void updateComboBoxEmployeeCheck() throws Exception {
        theView.getComboBoxEmployeeCheck().removeAllItems();
        String selectedItem = ((String) theView.getComboBoxDepartmentCheck().getSelectedItem());
        if (selectedItem == null){

        }else {
            if (selectedItem.equals("All")) {
                for (Employee e : company.getListEmployees()) {
                    theView.getComboBoxEmployeeCheck().addItem(e.getId() + " " + e.getLastname() + " " + e.getFirstname());
                }

                for (Manager m : company.getListManagers()) {
                    theView.getComboBoxEmployeeCheck().addItem("M " + m.getId() + " " + m.getLastname() + " " + m.getFirstname());
                }
                return;
            }

            if (selectedItem.equals("All")) {
                for (Employee e : company.getListEmployees()) {
                    if (!company.isInADepartment(e))
                        theView.getComboBoxEmployeeCheck().addItem(e.getId() + " " + e.getLastname() + " " + e.getFirstname());
                }

                for (Manager m : company.getListManagers()) {
                    if (!company.isInADepartment(m))
                        theView.getComboBoxEmployeeCheck().addItem("M " + m.getId() + " " + m.getLastname() + " " + m.getFirstname());
                }
                return;
            }

            for (Department d : company.getListDepartment()) {
                if (d.getName().equals(selectedItem)) {
                    for (Employee e : d.getListEmployees()) {
                        theView.getComboBoxEmployeeCheck().addItem(e.getId() + " " + e.getLastname() + " " + e.getFirstname());
                    }

                    for (Manager m : d.getListManagers()) {
                        theView.getComboBoxEmployeeCheck().addItem("M " + m.getId() + " " + m.getLastname() + " " + m.getFirstname());
                    }
                    return;
                }
            }
        }
    }

    private void updateTableChecks() throws Exception {

        String selectedItem = ((String) theView.getComboBoxEmployeeCheck().getSelectedItem());
        MyModelTable model = new MyModelTable();
        model.addColumn("Employee");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Reference Time");
        if(selectedItem == null){
            theView.getTableCheck().setModel(model);
            return;
        }

        if (selectedItem.split(" ")[0].equals("M")){
            Manager manager = company.searchManagerWithId(Integer.parseInt(selectedItem.split(" ")[1]));
            for (Check c:manager.getListCheck()) {
                if(c.getArrivingTime() != null){
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null){
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getDepartureTime()
                    });
                }
            }
        }else{
            Employee employee= company.searchEmployeeWithId(Integer.parseInt(selectedItem.split(" ")[0]));
            for (Check c:employee.getListCheck()) {
                if(c.getArrivingTime() != null){
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null){
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getDepartureTime()
                    });
                }

            }
        }
        theView.getTableCheck().setModel(model);
    }

    //</editor-fold desc = "Update Method">


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
                    throw new Exception("Please select an employee");
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
                int nbRow;
                nbRow = theView.getTableDepartment().getSelectedRow();
                if (nbRow == -1)
                    throw new Exception("Please select a department");
                String departmentName = theView.getTableDepartment().getValueAt(nbRow,0).toString();
                company.removeDepartment(company.searchDepartment(departmentName));
                updateTableDepartment();
            } catch (Exception e) {
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

    private class ButtonAddDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getComboBoxChief().getItemCount() < 1)
                    throw new Exception("You have to create a new manager");
                String sId = (String)theView.getComboBoxChief().getSelectedItem();
                int id = Integer.parseInt(sId.split(" ")[0]);
                Manager manager = company.searchManagerWithId(id);
                if (theView.getTextFieldNameDepartment().getText().equals("") || theView.getTextFieldNameDepartment().getText()==null)
                    throw new Exception("please enter a name for the department");
                Department department = new Department(theView.getTextFieldNameDepartment().getText(),manager);
                company.addDepartment(department);
                theView.getTextFieldNameDepartment().setText("");
                updateComboBoxChief();
                updateTableDepartment();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private class TextFieldSearchDepartmentListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            updateTableDepartment();
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            updateTableDepartment();
        }
    }

    private class ComboBoxDepartmentCheckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateComboBoxEmployeeCheck();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ComboBoxEmployeeCheckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableChecks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class tabbedPaneListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            try {
                updateComboBoxDepartment();
                updateTableStaff();
                updateComboBoxChief();
                updateTableDepartment();
                updateTableChecks();
                updateComboBoxDepartmentCheck();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ImportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(true);
                int select=fileChooser.showOpenDialog(theView);
                if(select==JFileChooser.APPROVE_OPTION){
                    fileChooser.getSelectedFile().getName();
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    int nb = company.importCSV(path);
                    if (nb == 0)
                        JOptionPane.showMessageDialog(null,"All the Employees have been successfully added to the company","Success",JOptionPane.INFORMATION_MESSAGE);
                    if (nb != 0)
                        JOptionPane.showMessageDialog(null,nb+" employee(s) have not been added to the company.\nProbably because of an id conflict","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"can not read the selected file.","Error",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private class ExportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String fileName = theView.getFileNameTextField().getText();
            if (fileName == null || fileName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int select=fileChooser.showOpenDialog(theView);
                if(select==JFileChooser.APPROVE_OPTION){
                    fileChooser.getSelectedFile().getName();
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    path = path +"/"+fileName+".csv";
                    company.exportCSV(path);
                    JOptionPane.showMessageDialog(null,"List of employee successfully exported","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"can not read the selected file.","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //</editor-fold>

}
