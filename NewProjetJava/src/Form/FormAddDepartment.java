package Form;

import Model.company.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormAddDepartment extends JDialog {
    private JPanel panel1;
    private JButton cancelButton;
    private JButton okButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTable table1;


    //<editor-fold desc = "Get and Set">

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    //</editor-fold>


    public FormAddDepartment(Frame frame, String s) {
        super(frame, s);
        setTitle("Add Department");
        setContentPane(panel1);
        setAlwaysOnTop(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
    }

    public void addButtonOkListener(ActionListener buttonOkListener){
        okButton.addActionListener(buttonOkListener);
    }

    public void addButtonCancelListener(ActionListener buttonCancelListener){
        cancelButton.addActionListener(buttonCancelListener);
    }

}
