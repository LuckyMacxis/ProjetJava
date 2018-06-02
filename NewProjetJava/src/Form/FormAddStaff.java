package Form;

import Model.company.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormAddStaff extends JDialog {

    //<editor-fold desc = "Attribute">
    private JButton cancelButton;
    private JButton okButton;
    private JCheckBox managerCheckBox;
    private JTextField textFieldFirstName;
    private JLabel nameLabel;
    private JTextField textFieldLastName;
    private JTextField textFieldMail;
    private JTextField textFieldArriving;
    private JTextField textFieldDeparture;
    private JComboBox comboBox1;
    private JPanel panel1;

    private ArrayList<Department> listDepartment;

    //</editor-fold>

    //<editor-fold desc="Get and Set">

    public JCheckBox getManagerCheckBox() {
        return managerCheckBox;
    }

    public JTextField getTextFieldFirstName() {
        return textFieldFirstName;
    }

    public JTextField getTextFieldLastName() {
        return textFieldLastName;
    }

    public JTextField getTextFieldMail() {
        return textFieldMail;
    }

    public JTextField getTextFieldArriving() {
        return textFieldArriving;
    }

    public JTextField getTextFieldDeparture() {
        return textFieldDeparture;
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
