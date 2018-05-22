package Tester;

import Model.Time.DateAndTime;
import Model.company.Department;
import Model.company.Employee;
import Model.company.Manager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterDepartment {
    private Department department;
    private Manager manager;

    @Before
    public void setUp(){
        try {
            manager = new Manager("manager1","m",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            department = new Department("department1",manager);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void Test() {
        try{
            setUp();
            assertEquals("department1",department.getName());
            assertTrue(department.getListManagers().get(0).isChief());
            department.addEmployee(new Employee("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME)));
            department.addEmployee(new Employee("2","2",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME)));
            department.addEmployee(new Employee("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME)));
            assertEquals(3,department.getListEmployees().size());
            Manager manager1 = new Manager("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            department.addManager(manager1);
            assertEquals(2,department.getListManagers().size());
            department.changeChief(manager1);
            assertTrue(manager1.isChief());
            assertTrue(!manager.isChief());
        }catch (Exception e){
            System.out.println(e);
            fail("it shouldn't have thrown an Exception");
        }

    }

    @Test
    public void failureTest(){
        try {
            //try to add a null department
            setUp();
            Department d = new Department(null,manager);
            fail("it should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("null arguments",e.getMessage());
        }
        try {
            // try to add two time the same employee
            setUp();
            Employee e = new Employee("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            department.addEmployee(e);
            department.addEmployee(e);
            fail("it should have thrown an Exception, we can add two time the same employee");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this employee is already in this department",e.getMessage());
        }
        try {
            // try to add two time the same manager
            setUp();
            Manager m = new Manager("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            department.addManager(m);
            department.addManager(m);
            fail("it should have thrown an Exception, we can add two time the same manager");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this employee is already in this department",e.getMessage());
        }
        try{
            // try to set as chief a manager which isn't in the department
            Manager m = new Manager("1","1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            department.changeChief(m);
            fail("it should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("this Manager isn't in this department",e.getMessage());
        }
    }
}
