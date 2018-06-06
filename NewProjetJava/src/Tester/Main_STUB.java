package Tester;

import Model.Time.Check;
import Model.Time.DateAndTime;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;

import java.awt.*;

public class Main_STUB {

    public static void Stub(Company company) {
        try{

            //create 3 employees and 3 manager
            Employee employee1 = new Employee("Jean","Bon",
                                              new DateAndTime("10:00",DateAndTime.TIME),
                                              new DateAndTime("15:00",DateAndTime.TIME));
            employee1.addCheck(new Check(employee1,new DateAndTime("13/05/1997 11:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("13/05/1997 15:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("14/05/1997 11:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("14/05/1997 15:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("15/05/1997 11:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("15/05/1997 15:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("16/05/1997 11:00",DateAndTime.DATE_TIME)));
            employee1.addCheck(new Check(employee1,new DateAndTime("16/05/1997 15:00",DateAndTime.DATE_TIME)));


            Employee employee2 = new Employee("David","Yann",
                                               new DateAndTime("8:00",DateAndTime.TIME),
                                               new DateAndTime("18:00",DateAndTime.TIME));
            Employee employee3 = new Employee("Martin","PECHEUR",
                                               new DateAndTime("10:00",DateAndTime.TIME),
                                               new DateAndTime("15:00",DateAndTime.TIME));
            Manager manager1 = new Manager("Carbonnier","Etienne",
                                          new DateAndTime("7:45",DateAndTime.TIME),
                                          new DateAndTime("22:45",DateAndTime.TIME));
            Manager manager4 = new Manager("Nicolas","Vi",
                    new DateAndTime("7:45",DateAndTime.TIME),
                    new DateAndTime("22:45",DateAndTime.TIME));

            Employee employee4 = new Employee("Thomas","Brique",
                    new DateAndTime("10:00",DateAndTime.TIME),
                    new DateAndTime("15:00",DateAndTime.TIME));
            Manager manager2 = new Manager("Olivier","Philip",
                                            new DateAndTime("5:45",DateAndTime.TIME),
                                            new DateAndTime("20:45",DateAndTime.TIME));
            Manager manager3 = new Manager("Drame","Moussa",
                                            new DateAndTime("7:45",DateAndTime.TIME),
                                            new DateAndTime("22:45",DateAndTime.TIME));

            // add them in the company
            company.addEmployee(employee1);
            company.addEmployee(employee2);
            company.addEmployee(employee3);
            company.addManager(manager1);
            company.addManager(manager2);
            company.addManager(manager3);
            company.addEmployee(employee4);
            company.addManager(manager4);

            //create 3 department
            Department department1 = new Department("R&D",manager1);
            Department department2 = new Department("Info",manager2);
            Department department3 = new Department("Com",manager3);

            //add those department in the company
            company.addDepartment(department1);
            company.addDepartment(department2);
            company.addDepartment(department3);

            //assign an employee in each department
            company.addEmployeeToDepartment(employee1,department1);
            company.addEmployeeToDepartment(employee2,department2);
            company.addEmployeeToDepartment(employee3,department3);

            company.addCheckToManager(manager3,new Check(manager3,new DateAndTime("22/05/1997 11:00",DateAndTime.DATE_TIME)));

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("a Company with 3 department an 6 employees");

    }
}
