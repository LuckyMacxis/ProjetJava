package Tester;

import Controller.MainFormController;
import Form.MainForm;
import Model.company.Company;

public class Main {

    public static void main(String[] args) throws Exception {
        MainForm form = new MainForm();

        Company company = new Company();
        Main_STUB.Stub(company);
        company.serialize();
        //company = Company.deserialize();
        MainFormController mainController = new MainFormController(form, company);
        form.setVisible(true);

    }
}
