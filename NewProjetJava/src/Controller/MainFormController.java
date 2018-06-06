package Controller;

import Form.*;
import Model.Time.Check;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;
import TCP.client.Client;
import TCP.server.Server;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class MainFormController {
    private MainForm theView;

    private Company company;

    private Server server;

    private Client client;

    private int incidentThreshold = 30;

    /**
     * this is the model used in my JTable, with it cells aren't alterable
     */
    public class MyModelTable extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    }

    /**
     * Construct the controller of the main view
     * @param mainForm MainForm, theView, a JFrame
     * @param company Company, the model
     */
    public MainFormController(MainForm mainForm, Company company) {
        this.theView = mainForm;
        this.company = company;

        theView.addComboBoxDepartmentListener(new ComboBoxDepartmentListener());
        theView.addButtonDeleteFromCompanyListener(new ButtonDeleteFromCompanyListener());
        theView.addButtonDeleteFromDepartmentListener(new ButtonDeleteFromDepartmentListener());
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
        theView.textFieldSearchStaffListener(new TextFieldSearchStaffListener());
        theView.assignButtonListener(new AssignButtonListener());
        theView.addStaffButtonListener(new AddStaffButtonListener());
        theView.okButtonListener(new OkButtonListener());
        theView.validateButtonListener(new ValidateButtonListener());
        theView.radioButtonChecksListener(new radioButtonChecksListener());
        theView.editStaffButtonListener(new EditStaffButtonListener());
        theView.closingListener(new ClosingListener());

        theView.getTableStaff().setRowSelectionAllowed(true);
        theView.getTableStaff().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        theView.getTableStaff().getTableHeader().setReorderingAllowed(false);

        try {
            server = new Server();
            server.start();

            client = new Client("tmp/check.serial",company);
            client.start();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
        }

        updateComboBoxDepartment();
        try {
            updateTableStaff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc = "Update Method">

    /**
     * Update the object inside the comboBox of department
     */
    private void updateComboBoxDepartment(){
        theView.getComboBoxDepartment().removeAllItems();
        theView.getComboBoxDepartment().addItem("All");
        theView.getComboBoxDepartment().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartment().addItem(d.getName());
        }
    }

    /**
     * update the values inside the table of employee according to the filters
     * @throws Exception if we try to display something that doesn't exist in the company
     */
    private void updateTableStaff() throws Exception {

        MyModelTable modelTableStaff = new MyModelTable();

        modelTableStaff.addColumn("Id");
        modelTableStaff.addColumn("Name");
        modelTableStaff.addColumn("Mail");
        modelTableStaff.addColumn("Function");
        modelTableStaff.addColumn("Arriving Time");
        modelTableStaff.addColumn("Departure Time");
        modelTableStaff.addColumn("Additional Time");

        if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("All")){
            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : company.getListManagers()) {
                    if (m.isChief()) {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname()+" "+m.getLastname(),
                                    m.getMail(),
                                    "Chief",
                                    m.getArrivingTime().toString(),
                                    m.getDepartureTime().toString(),
                                    m.getAdditionalTime()
                            });
                        }
                    } else {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname()+" "+m.getLastname(),
                                    m.getMail(),
                                    "Manager",
                                    m.getArrivingTime().toString(),
                                    m.getDepartureTime().toString(),
                                    m.getAdditionalTime()
                            });
                        }
                    }
                }
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : company.getListEmployees()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                        modelTableStaff.addRow(new Object[]{
                                e.getId(),
                                e.getFirstname()+" "+e.getLastname(),
                                e.getMail(),
                                "Employee",
                                e.getArrivingTime().toString(),
                                e.getDepartureTime().toString(),
                                e.getAdditionalTime()
                        });
                    }
                }
            }
        }

        if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("None")){

            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : company.getListManagers()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                        if (!company.isInADepartment(m))
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname()+" "+m.getLastname(),
                                    m.getMail(),
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
                    if (!company.isInADepartment(e)) {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    e.getId(),
                                    e.getFirstname()+" "+e.getLastname(),
                                    e.getMail(),
                                    "Employee",
                                    e.getArrivingTime().toString(),
                                    e.getDepartureTime().toString(),
                                    e.getAdditionalTime()
                            });
                        }
                    }
                }
            }
        }

        if (!theView.getComboBoxDepartment().getSelectedItem().toString().equals("None") && !theView.getComboBoxDepartment().getSelectedItem().toString().equals("All")){
            Department department = company.searchDepartment(theView.getComboBoxDepartment().getSelectedItem().toString());
            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : department.getListManagers()) {
                    if (m.isChief()) {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname()+" "+m.getLastname(),
                                    m.getMail(),
                                    "Chief",
                                    m.getArrivingTime().toString(),
                                    m.getDepartureTime().toString(),
                                    m.getAdditionalTime()
                            });
                        }
                    } else {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname()+" "+m.getLastname(),
                                    m.getMail(),
                                    "Manager",
                                    m.getArrivingTime().toString(),
                                    m.getDepartureTime().toString(),
                                    m.getAdditionalTime()
                            });
                        }
                    }
                }
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : department.getListEmployees()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                        modelTableStaff.addRow(new Object[]{
                                e.getId(),
                                e.getFirstname()+" "+e.getLastname(),
                                e.getMail(),
                                "Employee",
                                e.getArrivingTime().toString(),
                                e.getDepartureTime().toString(),
                                e.getAdditionalTime()
                        });
                    }
                }
            }
        }
        theView.getTableStaff().setModel(modelTableStaff);

    }

    /**
     * update the comboBox of Available chief in the department tab
     */
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

    /**
     * update the table of department according to the filters
     */
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

    /**
     * update the table of checks according to the filters and the incidence threshold value
     */
    private void updateComboBoxDepartmentCheck(){
        theView.getComboBoxDepartmentCheck().removeAllItems();
        theView.getComboBoxDepartmentCheck().addItem("All");
        theView.getComboBoxDepartmentCheck().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartmentCheck().addItem(d.getName());
        }
    }

    /**
     * update the value inside the comboBox of employee / managers in the checks tab
     * @throws Exception if we try to display something that doesn't exist in the company
     */
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

    /**
     * update the values inside the table of checks according to the filters
     * @throws Exception if we try to display something that doesn't exist in the company
     */
    private void updateTableChecks() throws Exception {

        String selectedEmployee = ((String) theView.getComboBoxEmployeeCheck().getSelectedItem());
        MyModelTable model = new MyModelTable();
        model.addColumn("Employee");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Reference Time");
        if(selectedEmployee == null){
            theView.getTableCheck().setModel(model);
            return;
        }
        String time;
        String date;

        if (selectedEmployee.split(" ")[0].equals("M")){
            Manager manager = company.searchManagerWithId(Integer.parseInt(selectedEmployee.split(" ")[1]));
            for (Check c:manager.getListCheck()) {
                if(c.getArrivingTime() != null && (theView.getAllChecksRadioButton().isSelected() || (c.getCheck().getDate().equals(LocalDate.now())))){
                    if (c.getCheck().getTime().getHour()*60+c.getCheck().getTime().getMinute()
                            - (c.getArrivingTime().getTime().getHour()*60+c.getArrivingTime().getTime().getMinute())
                            > incidentThreshold){
                        date = "<html><font color=\"red\">"+c.getCheck().getDate().toString()+"</font></html>";
                        time = "<html><font color=\"red\">"+c.getCheck().getTime().toString()+"</font></html>";
                    }else{
                        date = c.getCheck().getDate().toString();
                        time = c.getCheck().getTime().toString();
                    }
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            date,
                            time,
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null && (theView.getAllChecksRadioButton().isSelected() || (c.getCheck().getDate().equals(LocalDate.now())))){
                    if (c.getCheck().getTime().getHour()*60+c.getCheck().getTime().getMinute()
                            - (c.getDepartureTime().getTime().getHour()*60+c.getDepartureTime().getTime().getMinute())
                            < -incidentThreshold){
                        date = "<html><font color=\"red\">"+c.getCheck().getDate().toString()+"</font></html>";
                        time = "<html><font color=\"red\">"+c.getCheck().getTime().toString()+"</font></html>";
                    }else{
                        date = c.getCheck().getDate().toString();
                        time = c.getCheck().getTime().toString();
                    }
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            date,
                            time,
                            c.getDepartureTime()
                    });
                }
            }
        }else{
            Employee employee= company.searchEmployeeWithId(Integer.parseInt(selectedEmployee.split(" ")[0]));
            for (Check c:employee.getListCheck()) {
                if(c.getArrivingTime() != null && (theView.getAllChecksRadioButton().isSelected() || (c.getCheck().getDate().equals(LocalDate.now())))){
                    if (c.getCheck().getTime().getHour()*60+c.getCheck().getTime().getMinute()
                            - (c.getArrivingTime().getTime().getHour()*60+c.getArrivingTime().getTime().getMinute())
                            > incidentThreshold){
                        date = "<html><font color=\"red\">"+c.getCheck().getDate().toString()+"</font></html>";
                        time = "<html><font color=\"red\">"+c.getCheck().getTime().toString()+"</font></html>";
                    }else{
                        date = c.getCheck().getDate().toString();
                        time = c.getCheck().getTime().toString();
                    }
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            date,
                            time,
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null && (theView.getAllChecksRadioButton().isSelected() || (c.getCheck().getDate().equals(LocalDate.now())))){
                    if (c.getCheck().getTime().getHour()*60+c.getCheck().getTime().getMinute()
                            - (c.getDepartureTime().getTime().getHour()*60+c.getDepartureTime().getTime().getMinute())
                            < -incidentThreshold){
                        date = "<html><font color=\"red\">"+c.getCheck().getDate().toString()+"</font></html>";
                        time = "<html><font color=\"red\">"+c.getCheck().getTime().toString()+"</font></html>";
                    }else{
                        date = c.getCheck().getDate().toString();
                        time = c.getCheck().getTime().toString();
                    }
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            date,
                            time,
                            c.getDepartureTime()
                    });
                }

            }
        }
        theView.getTableCheck().setModel(model);
    }

    /**
     * update the comboBox of available staff in the department tab
     * @throws Exception if we try to display something that doesn't exist in the company
     */
    public void updateComboBoxAvailableStaff() throws Exception {
        theView.getComboBoxAvailableStaff().removeAllItems();
        for (Manager m : company.getListManagers()) {
            if (!company.isInADepartment(m))
                theView.getComboBoxAvailableStaff().addItem("M"+" "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
        }

        for (Employee e : company.getListEmployees()) {
            if (!company.isInADepartment(e))
                theView.getComboBoxAvailableStaff().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
        }
    }

    /**
     * update the values inside the textFields in  the parameter tab with the currents values of those parameters
     */
    public void updateTextFieldParameter(){
        theView.getTextFieldPortServer().setText(Integer.toString(server.getPort()));
        theView.getTextFieldPortClient().setText(Integer.toString(client.getPort()));
        theView.getTextFieldIp().setText(client.getAddress());
        theView.getTextFieldIncidence().setText(Integer.toString(incidentThreshold));
    }

    //</editor-fold desc = "Update Method">

    //<editor-fold desc = "Class">

    private class ComboBoxDepartmentListener implements ActionListener {
        /**
         * update the table of employee when we select a department
         */
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

    private class ButtonDeleteFromCompanyListener implements ActionListener {
        /**
         * delete an employee / a manager from the company, then serialize the company
         */
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
                company.serialize();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private class ButtonDeleteFromDepartmentListener implements ActionListener {
        /**
         * remove an employee / a manager from his department, then serialize the company
         */
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
                company.serialize();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ButtonDeleteDepartmentListener implements ActionListener {
        /**
         * delete a department from the company, then serialize the company
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                int nbRow;
                nbRow = theView.getTableDepartment().getSelectedRow();
                if (nbRow == -1)
                    throw new Exception("Please select a department");
                String departmentName = theView.getTableDepartment().getValueAt(nbRow,0).toString();
                company.removeDepartment(company.searchDepartment(departmentName));
                updateTableDepartment();
                company.serialize();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class RadioButtonListener implements ActionListener {
        /**
         * update the table of employee when an other radio button is selected
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonSetAsChiefListener implements ActionListener {
        /**
         * set the selected manager as the chief of his department, then serialize the company
         */
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
                company.serialize();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class ButtonAddDepartmentListener implements ActionListener {
        /**
         * create and add a department to the company, depending on the selected chief and the name of the department
         * , then serialize the company
         */
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
                updateComboBoxAvailableStaff();
                company.serialize();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private class TextFieldSearchDepartmentListener implements KeyListener {

        public void keyTyped(KeyEvent keyEvent) {
        }

        /**
         * update the table of employee
         */
        public void keyPressed(KeyEvent keyEvent) {
            updateTableDepartment();
        }

        /**
         * update the table of employee
         */
        public void keyReleased(KeyEvent keyEvent) {
            updateTableDepartment();
        }
    }

    private class ComboBoxDepartmentCheckListener implements ActionListener {
        /**
         * update the comboBox of employee in the checks tab
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateComboBoxEmployeeCheck();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ComboBoxEmployeeCheckListener implements ActionListener {
        /**
         * update the table of checks
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableChecks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class tabbedPaneListener implements ChangeListener {
        /**
         * update all the comboBox, table, and TextFields of all tabs
         */
        public void stateChanged(ChangeEvent changeEvent) {
            try {
                updateComboBoxDepartment();
                updateTableStaff();
                updateComboBoxChief();
                updateTableDepartment();
                updateTableChecks();
                updateComboBoxDepartmentCheck();
                updateComboBoxAvailableStaff();
                updateTextFieldParameter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ImportButtonListener implements ActionListener {
        /**
         * Import a list of employee from a csv file, then serialize the company
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(true);
                int select=fileChooser.showOpenDialog(theView);
                if(select==JFileChooser.APPROVE_OPTION){
                    fileChooser.getSelectedFile().getName();
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    company.importCSV(path);
                    company.serialize();
                        JOptionPane.showMessageDialog(null,"All the Employees have been successfully added to the company","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"can not read the selected file.","Error",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private class ExportButtonListener implements ActionListener {
        /**
         * export all the employees / managers of the company in a csv file
         */
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
                    JOptionPane.showMessageDialog(null,"List of employees successfully exported","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"can not read the selected file.","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class TextFieldSearchStaffListener implements KeyListener {
        public void keyTyped(KeyEvent keyEvent) {

        }

        /**
         * update the table of employees
         */
        public void keyPressed(KeyEvent keyEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * update the table of employees
         */
        public void keyReleased(KeyEvent keyEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class AssignButtonListener implements ActionListener {
        /**
         * assign an available employee / manager to a department, then serialize the company
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                int nbRow;
                nbRow = theView.getTableDepartment().getSelectedRow();
                if (nbRow == -1)
                    throw new Exception("Please select a department");
                String departmentName = theView.getTableDepartment().getValueAt(nbRow,0).toString();
                String[] tab = theView.getComboBoxAvailableStaff().getSelectedItem().toString().split(" ");
                if (tab[0].equals("M")){
                    company.addManagerToDepartment(company.searchManagerWithId(Integer.parseInt(tab[1])),company.searchDepartment(departmentName));
                }else{
                    company.addEmployeeToDepartment(company.searchEmployeeWithId(Integer.parseInt(tab[0])),company.searchDepartment(departmentName));
                }
                updateTableDepartment();
                updateComboBoxAvailableStaff();
                updateComboBoxChief();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class AddStaffButtonListener implements ActionListener {
        /**
         * display the frame to add an employee / a manager, then serialize the company
         */
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

    private class OkButtonListener implements ActionListener {
        /**
         * update the parameters of the TCP client and the TCP server, then export those new value in the config.xml file
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getTextFieldIp().getText().equals("") || theView.getTextFieldIp().getText() == null
                        || theView.getTextFieldPortClient().getText().equals("") || theView.getTextFieldPortClient().getText() == null
                        || theView.getTextFieldPortServer().getText().equals("") || theView.getTextFieldPortServer().getText() == null) {
                    throw new Exception("Please enter valid Ip and ports");
                } else {
                    String portServer = theView.getTextFieldPortServer().getText();
                    String portClient = theView.getTextFieldPortClient().getText();
                    String address = theView.getTextFieldIp().getText();
                    Properties config = new Properties();
                    config.loadFromXML(new FileInputStream("config/config.xml"));
                    config.setProperty("Ip",address);
                    config.setProperty("portServer",portServer);
                    config.setProperty("portClient",portClient);
                    server.setPort(Integer.parseInt(portServer));
                    client.setAddressPort(address,Integer.parseInt(portClient));
                    config.storeToXML(new FileOutputStream("config/config.xml"),null);
                }
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
            updateTextFieldParameter();
        }
    }


    private class ValidateButtonListener implements ActionListener {
        /**
         * modify the value of the incidence threshold
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (theView.getTextFieldIncidence().getText().equals("") || theView.getTextFieldIncidence().getText() == null)
                    throw new Exception("");
                incidentThreshold = Integer.parseInt(theView.getTextFieldIncidence().getText());
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Please enter a valid integer","Error",JOptionPane.INFORMATION_MESSAGE);
            }
            updateTextFieldParameter();
        }
    }

    private class radioButtonChecksListener implements ActionListener {
        /**
         * upadate the table of checks
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableChecks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class EditStaffButtonListener implements ActionListener {
        /**
         * display the frame to edit an employee / a manager, then serialise the company
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                EditForm editForm = new EditForm();
                int nbRow = theView.getTableStaff().getSelectedRow();
                if (nbRow == -1)
                    throw new Exception("Please select an employee");
                int id =  Integer.parseInt(theView.getTableStaff().getValueAt(nbRow,0).toString());
                Employee employee;
                if (theView.getTableStaff().getValueAt(nbRow,3).toString().equals("Employee")){
                    employee = company.searchEmployeeWithId(id);
                }else{
                    employee = company.searchManagerWithId(id);
                }
                EditStaffController editStaffController = new EditStaffController(editForm,employee);

                editForm.setVisible(true);
                updateTableStaff();
                company.serialize();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class ClosingListener extends WindowAdapter {
        /**
         * serialise the company before to close the application
         */
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            company.serialize();
        }
    }


    //</editor-fold>

}
