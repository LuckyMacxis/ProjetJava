package Tester;

import Model.Time.DateAndTime;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class TesterDateAndTime {

    @Test
    public void basicTimeTest(){
        try{
            DateAndTime time = new DateAndTime("10:30",DateAndTime.TIME);
            assertEquals(10,time.getTime().getHour());
            assertEquals(3,(new DateAndTime("3:30",DateAndTime.TIME)).getTime().getHour());
            assertEquals(0,(new DateAndTime("0:30",DateAndTime.TIME)).getTime().getHour());
            assertEquals(15,(new DateAndTime("12:15",DateAndTime.TIME)).getTime().getMinute());
            assertEquals(0,(new DateAndTime("12:0",DateAndTime.TIME)).getTime().getMinute());
            assertEquals(45,(new DateAndTime("12:45",DateAndTime.TIME)).getTime().getMinute());
            assertTrue(time.getDate() == null);

            DateAndTime date_time = new DateAndTime("13/5/1997 2:15",DateAndTime.DATE_TIME);
            assertEquals(13,date_time.getDate().getDayOfMonth());
            assertEquals(5,date_time.getDate().getMonthValue());
            assertEquals(1997,date_time.getDate().getYear());
            assertEquals(2,date_time.getTime().getHour());
            assertEquals(15,date_time.getTime().getMinute());


        }catch (Exception e){
            fail("it shouldn't have thrown an Exception");
        }

    }

    @Test
    public void roundTimeTest(){
        try{

            assertEquals(3,(new DateAndTime("3:42",DateAndTime.TIME)).getTime().getHour());
            assertEquals(0,(new DateAndTime("0:7",DateAndTime.TIME)).getTime().getHour());
            assertEquals(15,(new DateAndTime("12:17",DateAndTime.TIME)).getTime().getMinute());
            assertEquals(0,(new DateAndTime("12:7",DateAndTime.TIME)).getTime().getMinute());
            assertEquals(45,(new DateAndTime("12:38",DateAndTime.TIME)).getTime().getMinute());

            DateAndTime test = new DateAndTime("10:55",DateAndTime.TIME);
            assertEquals(0,test.getTime().getMinute());
            assertEquals(11,test.getTime().getHour());

        }catch (Exception e){
            fail("it shouldn't have thrown an Exception");
        }
    }

    @Test
    public void badStringToInit(){
        try {
            DateAndTime test1 = new DateAndTime("10,54",DateAndTime.TIME);
            fail("It should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("Wrong String to Init, the right form is \"hh:mm\"",e.getMessage());
        }
        try {
            DateAndTime test2 = new DateAndTime("10,54:12",DateAndTime.TIME);
            fail("It should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("Impossible to convert into an Integer",e.getMessage());
        }
        try {
            DateAndTime test3 = new DateAndTime("25:12",DateAndTime.TIME);
            fail("It should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("minute or hour out of range",e.getMessage());
        }
        try{
            DateAndTime test4 = new DateAndTime("12:5:8996 25:12",DateAndTime.DATE_TIME);
            fail("It should have thrown an Exception");
        }catch (Exception e){
            System.out.println(e);
            assertEquals("String input wrong format for date and time, the right form is \"dd/mm/yyyy hh:mm\"",e.getMessage());
        }
    }

    @Test
    public void cloneTest(){
        try {
            DateAndTime dateAndTime = new DateAndTime("10/5/1997 10:15",DateAndTime.DATE_TIME);
            DateAndTime dateAndTime1 = dateAndTime.clone();
            assertEquals(10,dateAndTime1.getTime().getHour());
            assertEquals(15,dateAndTime1.getTime().getMinute());
            assertEquals(10,dateAndTime1.getDate().getDayOfMonth());
            assertEquals(5,dateAndTime1.getDate().getMonthValue());
            assertEquals(1997,dateAndTime1.getDate().getYear());

            dateAndTime.setTime(LocalTime.of(20,14));
            dateAndTime.setDate(LocalDate.of(1998,11,22));
            assertEquals(20,dateAndTime.getTime().getHour());
            assertEquals(10,dateAndTime1.getTime().getHour());

            assertEquals(10,dateAndTime1.getDate().getDayOfMonth());
            assertEquals(22,dateAndTime.getDate().getDayOfMonth());



        }catch (Exception e){
            System.out.println(e);
            fail("it shouldn't have thrown an Exception");
        }
    }


}
