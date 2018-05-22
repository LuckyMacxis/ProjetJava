package Form;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{

    private JMenuBar menuBar;
    private JMenu menuStaff;
    private JMenuItem menuAddStaff;
    private JMenuItem menuAssignStaff;
    private JMenu menuDepartment;
    private JMenuItem menuAddDepartment;
    private JMenu menuCheck;
    private JMenuItem menuEmployeeChecksHistory;
    private JPanel mainPanel;
    private JComboBox comboBoxDepartment;
    private JButton buttonDeleteFromDepartment;
    private JButton buttonDeleteFromCompany;
    private JButton deleteDepartmentButton;
    private JTable tableStaff;
    private JRadioButton managersOnlyRadioButton;
    private JRadioButton employeeOnlyRadioButton;
    private JRadioButton bothRadioButton;
    private JButton buttonSetAsChief;


    //<editor-fold desc = "Get and Set">


    public JTable getTableStaff() {
        return tableStaff;
    }

    public JRadioButton getManagersOnlyRadioButton() {
        return managersOnlyRadioButton;
    }

    public JRadioButton getEmployeeOnlyRadioButton() {
        return employeeOnlyRadioButton;
    }

    public JRadioButton getBothRadioButton() {
        return bothRadioButton;
    }

    public JComboBox getComboBoxDepartment() {
        return comboBoxDepartment;
    }

    
    //</editor-fold>

    //<editor-fold desc = "Constructors">

    public MainForm(){
        setSize(1000,300);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initMenu();
    }

    //</editor-fold>

    //<editor-fold desc = "Method">

    /**
     * Init the menu of the mainForm
     */
    private void initMenu(){

        menuBar = new JMenuBar();

        menuStaff = new JMenu("Staff");
        menuAddStaff = new JMenuItem("Add");
        menuAssignStaff = new JMenuItem("Assign");
        menuBar.add(menuStaff);
        menuStaff.add(menuAddStaff);
        menuStaff.add(menuAssignStaff);


        menuDepartment = new JMenu("Department");
        menuAddDepartment = new JMenuItem("Add");
        menuBar.add(menuDepartment);
        menuDepartment.add(menuAddDepartment);

        menuCheck = new JMenu("Check");
        menuEmployeeChecksHistory = new JMenuItem("Employees' checks history");
        menuBar.add(menuCheck);
        menuCheck.add(menuEmployeeChecksHistory);

        setJMenuBar(menuBar);


    }


    public void addStaffListener(ActionListener staffListener){
        menuAddStaff.addActionListener(staffListener);
    }

    public void assignStaffListener(ActionListener staffListener){
        menuAssignStaff.addActionListener(staffListener);
    }

    public void addDepartmentListener(ActionListener departmentListener) {
        menuAddDepartment.addActionListener(departmentListener);
    }

    public void employeeCheckHistoryListener(ActionListener checkListener){
        menuEmployeeChecksHistory.addActionListener(checkListener);
    }

    public void addComboBoxDepartmentListener(ActionListener comboBoxDepartmentListener){
        comboBoxDepartment.addActionListener(comboBoxDepartmentListener);
    }

    public void addButtonDeleteFromDepartmentListener(ActionListener buttonDeleteFromDepartmentListener){
        buttonDeleteFromDepartment.addActionListener(buttonDeleteFromDepartmentListener);
    }

    public void addButtonDeleteFromCompanyListener(ActionListener buttonDeleteFromCompanyListener){
        buttonDeleteFromCompany.addActionListener(buttonDeleteFromCompanyListener);
    }

    public void buttonDeleteDepartmentListener(ActionListener actionListener){
        deleteDepartmentButton.addActionListener(actionListener);
    }

    public void radioButtonListener(ActionListener actionListener){
        employeeOnlyRadioButton.addActionListener(actionListener);
        managersOnlyRadioButton.addActionListener(actionListener);
        bothRadioButton.addActionListener(actionListener);
    }

    public void buttonSetAsChiefListener(ActionListener actionListener){
        buttonSetAsChief.addActionListener(actionListener);
    }
    //</editor-fold>




}

