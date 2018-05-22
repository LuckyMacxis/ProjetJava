package Form;

import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormAddStaff extends JDialog {

    //<editor-fold desc = "Attribute">
    private JButton cancelButton;
    private JButton okButton;
    private JCheckBox managerCheckBox;
    private JTextField textField1;
    private JLabel nameLabel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox comboBox1;
    private JPanel panel1;

    private ArrayList<Department> listDepartment;

    //</editor-fold>

    //<editor-fold desc="Get and Set">


    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JCheckBox getManagerCheckBox() {
        return managerCheckBox;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public ArrayList<Department> getListDepartment() {
        return listDepartment;
    }

    //</editor-fold>

    public FormAddStaff(Frame frame, String string, ArrayList<Department> list){
        super(frame, string);
        setTitle("Add Department");
        setContentPane(panel1);
        setAlwaysOnTop(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
        listDepartment = list;
        comboBox1.addItem("None");
        for (Department d : listDepartment) {
            comboBox1.addItem(d.getName());
        }

    }

    public void addButtonOkListener(ActionListener buttonOkListener){
        okButton.addActionListener(buttonOkListener);
    }

    public void addButtonCancelListener(ActionListener buttonCancelListener){
        cancelButton.addActionListener(buttonCancelListener);
    }
}
