package Form;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.beans.VetoableChangeListener;

public class MainForm extends JFrame{

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
    private JTabbedPane tabbedPane1;
    private JTable jTableDepartment;
    private JTextField textFieldNameDepartment;
    private JComboBox comboBoxChief;
    private JButton addButton;
    private JComboBox comboBoxEmployeeCheck;
    private JComboBox comboBoxDepartmentCheck;
    private JTable tableCheck;
    private JTextField textFieldSearchDepartment;
    private JTextField textFieldIp;
    private JTextField textFieldPortServer;
    private JTextField fileNameTextField;
    private JButton importButton;
    private JButton exportButton;
    private JTextField textFieldSearchStaff;
    private JComboBox comboBoxAvailableStaff;
    private JButton assignButton;
    private JButton addStaffButton;
    private JButton okButton;
    private JTextField textFieldPortClient;


    //<editor-fold desc = "Get and Set">


    public JTextField getTextFieldIp() {
        return textFieldIp;
    }

    public JTextField getTextFieldPortServer() {
        return textFieldPortServer;
    }

    public JTextField getTextFieldPortClient() {
        return textFieldPortClient;
    }

    public JComboBox getComboBoxAvailableStaff() {
        return comboBoxAvailableStaff;
    }

    public JTable getTableDepartment() {
        return jTableDepartment;
    }

    public JTextField getTextFieldNameDepartment() {
        return textFieldNameDepartment;
    }

    public JComboBox getComboBoxChief() {
        return comboBoxChief;
    }

    public JComboBox getComboBoxEmployeeCheck() {
        return comboBoxEmployeeCheck;
    }

    public JComboBox getComboBoxDepartmentCheck() {
        return comboBoxDepartmentCheck;
    }

    public JTable getTableCheck() {
        return tableCheck;
    }

    public JTextField getTextFieldSearchDepartment() {
        return textFieldSearchDepartment;
    }

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

    public JTextField getFileNameTextField() {
        return fileNameTextField;
    }

    public JTextField getTextFieldSearchStaff() {
        return textFieldSearchStaff;
    }

    //</editor-fold>

    //<editor-fold desc = "Constructors">

    public MainForm(){
        setSize(1000,400);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //</editor-fold>

    //<editor-fold desc = "Method">

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

    public void tabbedPaneListener(ChangeListener changeListener){
        tabbedPane1.addChangeListener(changeListener);
    }

    public void buttonAddDepartmentListener(ActionListener actionListener){
        addButton.addActionListener(actionListener);
    }

    public void comboBoxEmployeeCheckListener(ActionListener actionListener){
        comboBoxEmployeeCheck.addActionListener(actionListener);
    }

    public void comboBoxDepartmentCheckListener(ActionListener actionListener){
        comboBoxDepartmentCheck.addActionListener(actionListener);
    }

    public void importButtonListener(ActionListener actionListener){
        importButton.addActionListener(actionListener);
    }

    public void exportButtonListener(ActionListener actionListener){
        exportButton.addActionListener(actionListener);
    }

    public void textFieldSearchDepartmentListener(KeyListener actionListener){
        textFieldSearchDepartment.addKeyListener(actionListener);
    }

    public void textFieldSearchStaffListener(KeyListener actionListener){
        textFieldSearchStaff.addKeyListener(actionListener);
    }

    public void assignButtonListener(ActionListener actionListener){
        assignButton.addActionListener(actionListener);
    }

    public void addStaffButtonListener(ActionListener actionListener){
        addStaffButton.addActionListener(actionListener);
    }

    public void okButtonListener(ActionListener actionListener){
        okButton.addActionListener(actionListener);
    }
    //</editor-fold>




}

