package Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormAssignStaff extends JDialog{
    private JPanel panel1;
    private JComboBox comboBoxStaff;
    private JComboBox comboBoxDepartment;
    private JButton cancelButton;
    private JButton assignButton;


    //<editor-fold desc = "get and set">
    public JComboBox getComboBoxStaff() {
        return comboBoxStaff;
    }

    public JComboBox getComboBoxDepartment() {
        return comboBoxDepartment;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getAssignButton() {
        return assignButton;
    }

    //</editor-fold>

    public FormAssignStaff(Frame frame, String s) {
        super(frame, s);
        setTitle("Add Department");
        this.setSize(400,150);
        setContentPane(panel1);
        setAlwaysOnTop(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    public void cancelButtonListener(ActionListener actionListener){
        cancelButton.addActionListener(actionListener);
    }

    public void assignButtonListener(ActionListener actionListener){
        assignButton.addActionListener(actionListener);
    }
}
