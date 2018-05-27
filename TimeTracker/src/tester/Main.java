package tester;

import TCP.server.Server;
import controller.MainFormController;
import form.MainForm;

public class Main {
    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        MainFormController mainFormController = new MainFormController(mainForm);
        mainForm.setVisible(true);
    }
}
