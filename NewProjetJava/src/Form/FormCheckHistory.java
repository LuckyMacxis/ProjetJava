package Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormCheckHistory extends JDialog{
    private JPanel panel1;
    private JComboBox comboBoxDepartment;
    private JComboBox comboBoxEmployee;
    private JTable tableChecks;
    private JScrollPane scrollPaneTable;

    public JTable getTableChecks() {
        return tableChecks;
    }

    public JComboBox getComboBoxDepartment() {
        return comboBoxDepartment;
    }

    public JComboBox getComboBoxEmployee() {
        return comboBoxEmployee;
    }


    public FormCheckHistory(Frame frame, String string){
        super(frame,string);
        this.setSize(500,350);
        setTitle(string);
        setContentPane(panel1);
        setAlwaysOnTop(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    public void addComboBoxDepartmentListener(ActionListener comboBoxDepartmentListener){
        comboBoxDepartment.addActionListener(comboBoxDepartmentListener);
    }

    public void addComboBoxEmployeeListener(ActionListener comboBoxEmployeeListener){
        comboBoxEmployee.addActionListener(comboBoxEmployeeListener);
    }
}
