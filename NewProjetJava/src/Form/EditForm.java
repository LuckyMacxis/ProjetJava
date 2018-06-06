package Form;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EditForm extends JDialog {
    private JButton cancelButton;
    private JButton okButton;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldMail;
    private JTextField textFieldArriving;
    private JTextField textFieldDeparture;
    private JPanel panel1;


    /**
     * construct a new EditForm object
     */
    public EditForm(){
        super();
        setContentPane(panel1);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Edit staff");
        this.pack();
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

    public void okButtonListener(ActionListener actionListener){
        okButton.addActionListener(actionListener);
    }

    public void cancelButtonListener(ActionListener actionListener){
        cancelButton.addActionListener(actionListener);
    }
}