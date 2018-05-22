package Tester;

import Model.Time.Check;
import Model.Time.DateAndTime;
import Model.company.Company;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterCompany {
    private Company company;
    private Manager manager;
    private Manager manager1;
    private Employee employee;
    private Employee employee1;
    private Department department;

    @Before
    public void setUp() throws Exception {
        company = new Company("myCompany");
        manager = new Manager("manager","m",new DateAndTime("8:15",DateAndTime.TIME),new DateAndTime("16:45",DateAndTime.TIME));
        manager1 = new Manager("manager1","m",new DateAndTime("8:15",DateAndTime.TIME),new DateAndTime("16:45",DateAndTime.TIME));
        employee = new Employee("employee","m",new DateAndTime("8:15",DateAndTime.TIME),new DateAndTime("16:45",DateAndTime.TIME));
        employee1 = new Employee("employee1","m",new DateAndTime("8:15",DateAndTime.TIME),new DateAndTime("16:45",DateAndTime.TIME));
        department = new Department("department",manager);
    }

    @Test
    public void basicTest(){
        try {
            setUp();
            // add an department
            company.addDepartment(department);
            assertEquals(1,company.getListDepartment().size());
            assertTrue(company.getListDepartment().get(0).getListManagers().get(0).isChief());

            // add Employee
            company.addEmployee(employee);
            company.addEmployee(employee1);
            assertEquals(2,company.getListEmployees().size());

            // add an Employee in a department
            company.addEmployeeToDepartment(employee,department);
            company.addEmployeeToDepartment(employee1,department);
            assertEquals(2,company.getListDepartment().get(0).getListEmployees().size());

            // remove an Employee from a department but not from the company
            company.removeEmployeeFromDepartment(employee1,department);
            assertEquals(1,company.getListDepartment().get(0).getListEmployees().size());
            assertEquals(2,company.getListEmployees().size());

            company.addEmployeeToDepartment(employee1,department);

            // remove an Employee from the company
            company.removeEmployee(employee1);
            assertEquals(1,company.getListDepartment().get(0).getListEmployees().size());
            assertEquals(1,company.getListEmployees().size());

            // add a manager in a department and set him as the new chief
            company.addManager(manager1);
            company.addManagerToDepartment(manager1,department);
            company.changeChiefOfDepartment(department,manager1);
            assertTrue(manager1.isChief());
            assertTrue(!manager.isChief());

            //remove a manager from the company
            company.removeManager(manager);
            assertEquals(1,company.getListManagers().size());
            assertEquals(1,company.getListDepartment().size());

            // add a check to an employee
            company.addCheckToEmployee(employee1,new Check(employee1,new DateAndTime("13/05/1997 10:15",DateAndTime.DATE_TIME)));
            assertEquals(-120,employee1.getAdditionalTime());

            // add a check to a manager
            company.addManager(manager);
            company.addCheckToManager(manager, new Check(manager,new DateAndTime("13/05/1997 10:45",DateAndTime.DATE_TIME)));
            assertEquals(-150,manager.getAdditionalTime());

        }catch (Exception e){
            System.out.println(e);
            fail("it should'n have thrown an Exception");
        }
    }

    @Test
    public void failureTest() throws Exception {
        setUp();
        company.addEmployee(employee);
        company.addManager(manager);
        company.addManager(manager1);
        company.addDepartment(department);
        try {
            // try to remove a manager which is the chief of a department
            company.removeManager(manager);
            fail("it should have thrown an Exception, because we can't remove the chief of a department");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("This manager is the chief of a department",e.getMessage());
            assertEquals(2,company.getListManagers().size());
        }
        try {
            // add a department which is already in the company
            company.addDepartment(department);
            fail("it should have thrown an Exception, because this department is already in the company");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this Department is already in the company",e.getMessage());
        }
        try {
            // add an Employee which is already in the company
            company.addEmployee(employee);
            fail("it should have thrown an Exception, because this employee is already in the company");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this employee is already in the company",e.getMessage());
        }
        try {
            // add a Manager which is already in the company
            company.addManager(manager);
            fail("it should have thrown an Exception, because this manager is already in the company");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this manager is already in the company",e.getMessage());
        }
    }

}
