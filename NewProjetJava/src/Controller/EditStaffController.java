package Controller;

import Form.EditForm;
import Model.company.Employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditStaffController {
    private EditForm theView;
    private Employee selectedEmployee;

    /**
     * Construct the controller of the view to Edit a staff member of the company
     * @param theView EditForm, a modal JFrame
     * @param employee
     */
    public EditStaffController(EditForm theView, Employee employee) {
        this.theView = theView;
        selectedEmployee = employee;

        theView.okButtonListener(new OkButtonListener());
        theView.cancelButtonListener(new CancelButtonListener());

        updateTextField();
    }

    /**
     * this method update the value inside the textField of this frame with the current value of the employee
     */
    public void updateTextField(){
        theView.getTextFieldFirstName().setText(selectedEmployee.getFirstname());
        theView.getTextFieldLastName().setText(selectedEmployee.getLastname());
        if (selectedEmployee.getMail() != null)
            theView.getTextFieldMail().setText(selectedEmployee.getMail());
        theView.getTextFieldArriving().setText(selectedEmployee.getArrivingTime().toString());
        theView.getTextFieldDeparture().setText(selectedEmployee.getDepartureTime().toString());
    }

    private class OkButtonListener implements ActionListener {
        /**
         * Validate the modification made to the selected employee / manager, and then close the Frame
         */
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                selectedEmployee.editEmployee(theView.getTextFieldLastName().getText(),theView.getTextFieldFirstName().getText(),theView.getTextFieldMail().getText(),theView.getTextFieldArriving().getText(),theView.getTextFieldDeparture().getText());
                theView.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
                updateTextField();
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        /**
         * doesn't validate the modification made to the selected employee / manager and close the frame
         */
        public void actionPerformed(ActionEvent actionEvent) {
            theView.dispose();
        }
    }
}
