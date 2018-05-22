package Tester;

import Model.Time.Check;
import Model.Time.DateAndTime;
import Model.company.Employee;
import Model.company.Manager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterPerson {
    private Employee employee;
    private Manager manager;

    @Before
    public void setUp(){
        try {
            employee = new Employee("Michel","M",new DateAndTime("10:15",DateAndTime.TIME),new DateAndTime("15:15",DateAndTime.TIME));
            manager = new Manager("1","1",new DateAndTime("10:15",DateAndTime.TIME),new DateAndTime("15:15",DateAndTime.TIME));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void basicTest(){
        try{
            assertEquals("M",employee.getFirstname());
            assertEquals("Michel",employee.getLastname());
            assertEquals(0,employee.getId());
            assertEquals("1",manager.getFirstname());
            assertEquals(1,manager.getId());
            assertTrue(!manager.isChief());
            manager.setChief(true);
            assertTrue(manager.isChief());
        }catch (Exception e){
            System.out.println(e);
            fail("it should'n have thrown an Exception");
        }
    }

    @Test
    public void failureTest(){
        try {
            // try to create an employee with null argument
            Employee e = new Employee(null,
                    null,
                    null,
                    null);
            fail("it should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("Person null arguments",e.getMessage());
        }
        try {
            // try to create a manager with null aruments
            manager = new Manager(null,"1",new DateAndTime("10:10",DateAndTime.TIME),new DateAndTime("10:10",DateAndTime.TIME));
            fail("it should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("Person null arguments",e.getMessage());
        }
    }

    @Test
    public void checkTest(){
        try {
            setUp();

            // check if we can add two checks to an employee and a manager
            // and if the value of additionTime is correctly updated
            Check check = new Check(employee,new DateAndTime("13/05/1997 10:30",DateAndTime.DATE_TIME));
            employee.addCheck(check);
            assertEquals(-15,employee.getAdditionalTime());
            Check check1 = new Check(employee,new DateAndTime("13/05/1997 16:00",DateAndTime.DATE_TIME));
            employee.addCheck(check1);
            assertEquals(30,employee.getAdditionalTime());

            try {
                //try to add a third check to an employee the same day
                Check check5 = new Check(employee, new DateAndTime("13/05/1997 17:45",DateAndTime.DATE_TIME));
                employee.addCheck(check5);
                fail("it should have thrown an exception");
            }catch (Exception e){
                System.out.println(e);
                assertEquals("this employee have already check twice today",e.getMessage());
            }

            // add an other check but on an other day
            Check check11 = new Check(employee,new DateAndTime("14/05/1997 10:30",DateAndTime.DATE_TIME));
            employee.addCheck(check11);
            assertEquals(15,employee.getAdditionalTime());

            Check check3 = new Check(manager,new DateAndTime("13/05/1997 10:30",DateAndTime.DATE_TIME));
            Check check4 = new Check(manager,new DateAndTime("13/05/1997 16:00",DateAndTime.DATE_TIME));
            manager.addCheck(check3);
            assertEquals(-15,manager.getAdditionalTime());
            manager.addCheck(check4);
            assertEquals(30,manager.getAdditionalTime());

            try {
                // try to add to a manager a check which is for an other employee
                manager.addCheck(check1);
                fail("it should have thrown an exception");
            }catch (Exception e){
                System.out.println(e);
                assertEquals("this check isn't assigned to this employee", e.getMessage());
            }

            try{
                //try to add an anterior check
                Check check12 = new Check(employee,new DateAndTime("11/05/1997 10:30",DateAndTime.DATE_TIME));
                employee.addCheck(check12);
                fail("it should have thrown an exception");
            }catch (Exception e){
                System.out.println(e);
                assertEquals("this check is too old",e.getMessage());
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            fail("it shouldn't have thrown an Exception");
        }

    }
}
