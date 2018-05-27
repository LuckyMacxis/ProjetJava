package Tester;

import Controller.MainFormController;
import Form.MainForm;
import Model.company.Company;
import TCP.client.Client;
import TCP.server.Server;

public class Main {

    public static void main(String[] args) throws Exception {
        new Thread(new Server()).start();
        MainForm form = new MainForm();

        Company company = new Company("MyCompany");
        Main_STUB.Stub(company);
        company.serialize();
        new Thread(new Client("tmp/check.serial",company)).start();
        //company = Company.deserialize();
        MainFormController mainController = new MainFormController(form, company);
        form.setVisible(true);

    }
}
