package Controller;

import Form.FormCheckHistory;
import Model.Time.Check;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChecksHistoryController {

    private FormCheckHistory theView;

    private Company company;

    public class ModelTableChecks extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    }

    public ChecksHistoryController(FormCheckHistory theView, Company company) {
        this.theView = theView;
        this.company = company;
        theView.addComboBoxDepartmentListener(new ComboBoxDepartmentListener());
        theView.addComboBoxEmployeeListener(new ComboBoxEmployeeListener());
        updateComboBoxDepartment();
    }

    private void updateComboBoxDepartment(){
        System.out.println("ok");
        theView.getComboBoxDepartment().removeAllItems();
        theView.getComboBoxDepartment().addItem("All");
        theView.getComboBoxDepartment().addItem("None");
        for (Department d : company.getListDepartment()) {
            theView.getComboBoxDepartment().addItem(d.getName());
        }
    }

    private void updateComboBoxEmployee(String selectedItem) throws Exception {
        theView.getComboBoxEmployee().removeAllItems();

        if (selectedItem.equals("All")){
            for (Employee e : company.getListEmployees()) {
                theView.getComboBoxEmployee().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
            }

            for (Manager m : company.getListManagers()) {
                theView.getComboBoxEmployee().addItem("M "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
            }
            return;
        }

        if (selectedItem.equals("All")){
            for (Employee e : company.getListEmployees()) {
                if (!company.isInADepartment(e))
                    theView.getComboBoxEmployee().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
            }

            for (Manager m : company.getListManagers()) {
                if (!company.isInADepartment(m))
                    theView.getComboBoxEmployee().addItem("M "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
            }
            return;
        }

        for (Department d : company.getListDepartment()) {
            if (d.getName().equals(selectedItem)){
                for (Employee e : d.getListEmployees()) {
                    theView.getComboBoxEmployee().addItem(e.getId()+" "+e.getLastname()+" "+e.getFirstname());
                }

                for (Manager m : d.getListManagers()) {
                    theView.getComboBoxEmployee().addItem("M "+m.getId()+" "+m.getLastname()+" "+m.getFirstname());
                }
                return ;
            }
        }
    }

    private void updateTableChecks(String selectedItem) throws Exception {

        ModelTableChecks model = new ModelTableChecks();
        model.addColumn("Employee");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Reference Time");
        if(selectedItem == null)
            return;

        if (selectedItem.split(" ")[0].equals("M")){
            Manager manager = company.searchManagerWithId(Integer.parseInt(selectedItem.split(" ")[1]));
            for (Check c:manager.getListCheck()) {
                if(c.getArrivingTime() != null){
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null){
                    model.addRow(new Object[]{
                            manager.getFirstname()+" "+manager.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getDepartureTime()
                    });
                }
            }
        }else{
            Employee employee= company.searchEmployeeWithId(Integer.parseInt(selectedItem.split(" ")[0]));
            System.out.println("employee list");
            System.out.println(employee.getListCheck().size());
            for (Check c:employee.getListCheck()) {
                if(c.getArrivingTime() != null){
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getArrivingTime()
                    });
                }
                if(c.getDepartureTime() != null){
                    model.addRow(new Object[]{
                            employee.getFirstname()+" "+employee.getLastname(),
                            c.getCheck().getDate(),
                            c.getCheck().getTime(),
                            c.getDepartureTime()
                    });
                }

            }
        }
        theView.getTableChecks().setModel(model);
    }

    //<editor-fold desc = "Class">
    private class ButtonBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            theView.dispose();
        }
    }

    private class ComboBoxDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateComboBoxEmployee(((String) theView.getComboBoxDepartment().getSelectedItem()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ComboBoxEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                updateTableChecks(((String) theView.getComboBoxEmployee().getSelectedItem()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //</editor-fold>
}
