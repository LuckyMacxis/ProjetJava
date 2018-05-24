package controller;

import Model.Time.Check;
import Model.company.Company;
import form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFormController {

    MainForm theView;
    Company company;
    ArrayList<Check> listCheck = new ArrayList<>();

    public MainFormController(MainForm mainForm){
        theView = mainForm;

        theView.buttonCheckListener(new ButtonCheckListener());
        theView.buttonOptionListener(new ButtonOptionListener());
        theView.buttonSyncListener(new ButtonSyncListener());
    }

    private class ButtonCheckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        }
    }

    private class ButtonOptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private class ButtonSyncListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
}
