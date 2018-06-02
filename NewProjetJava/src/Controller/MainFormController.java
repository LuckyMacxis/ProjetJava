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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    public class MyModelTable extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    }

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
                        if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
                            modelTableStaff.addRow(new Object[]{
                                    m.getId(),
                                    m.getFirstname(),
                                    m.getLastname(),
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
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : company.getListEmployees()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
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

        if (theView.getComboBoxDepartment().getSelectedItem().toString().equals("None")){

            if (theView.getBothRadioButton().isSelected() || theView.getManagersOnlyRadioButton().isSelected()) {
                for (Manager m : company.getListManagers()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || m.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || m.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
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
                }

            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : company.getListEmployees()) {
                    if (!company.isInADepartment(e)) {
                        if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                                || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
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
                                    m.getFirstname(),
                                    m.getLastname(),
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
            }
            if (theView.getBothRadioButton().isSelected() || theView.getEmployeeOnlyRadioButton().isSelected()) {
                for (Employee e : department.getListEmployees()) {
                    if (theView.getTextFieldSearchStaff().getText().equals("") || e.getFirstname().contains(theView.getTextFieldSearchStaff().getText())
                            || e.getLastname().contains(theView.getTextFieldSearchStaff().getText())) {
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
        String time;
        String date;

        if (selectedItem.split(" ")[0].equals("M")){
            Manager manager = company.searchManagerWithId(Integer.parseInt(selectedItem.split(" ")[1]));
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
            Employee employee= company.searchEmployeeWithId(Integer.parseInt(selectedItem.split(" ")[0]));
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

    public void updateTextFieldParameter(){
        theView.getTextFieldPortServer().setText(Integer.toString(server.getPort()));
        theView.getTextFieldPortClient().setText(Integer.toString(client.getPort()));
        theView.getTextFieldIp().setText(client.getAddress());
        theView.getTextFieldIncidence().setText(Integer.toString(incidentThreshold));
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
                updateComboBoxAvailableStaff();
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
                updateComboBoxAvailableStaff();
                updateTextFieldParameter();
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
                    JOptionPane.showMessageDialog(null,"List of employees successfully exported","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"can not read the selected file.","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class TextFieldSearchStaffListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            try {
                updateTableStaff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class AssignButtonListener implements ActionListener {
        @Override
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

    private class OkButtonListener implements ActionListener {
        @Override
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
        @Override
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
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableChecks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //</editor-fold>

}
