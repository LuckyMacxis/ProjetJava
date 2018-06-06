package Model.Time;

import Model.company.Company;
import Model.company.Employee;

import java.io.*;

public class Check implements Serializable{

    private DateAndTime check;

    private Employee employee;

    private DateAndTime arrivingTime = null;

    private DateAndTime departureTime = null;

    //<editor-fold desc = "Constructors">

    /**
     * Constructor with parameters
     * @param check a Check
     * @param employee an Employee
     * @throws Exception if arguments are null
     */
    public Check(DateAndTime check, Employee employee) throws Exception {
        if (check == null || employee == null)
            throw new Exception("arguments null");
        this.check = check;
        this.employee = employee;
    }

    /**
     * Constructor without parameter
     */
    public Check(){
        check = null;
    }

    /**
     * Constructor with an employee and a DateAndTime
     * @param employee an Employee
     * @param checkTime a DateAndTime
     * @throws Exception if arguments are null
     */
    public Check(Employee employee, DateAndTime checkTime) throws Exception {
        if (employee == null || checkTime == null || checkTime.getDate() == null)
            throw new Exception("Check null arguments");
        this.employee = employee;
        arrivingTime = employee.getArrivingTime().clone();
        departureTime = employee.getDepartureTime().clone();
        check = checkTime;
    }

    //</editor-fold>

    //<editor-fold desc = "Get and Set">


    public void setArrivingTime(DateAndTime arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public void setDepartureTime(DateAndTime departureTime) {
        this.departureTime = departureTime;
    }

    public DateAndTime getCheck() {
        return check;
    }

    public void setCheck(DateAndTime check) {
        this.check = check;
    }

    public Employee getEmployee() {
        return employee;
    }

    public DateAndTime getArrivingTime() {
        return arrivingTime;
    }

    public DateAndTime getDepartureTime() {
        return departureTime;
    }

    //</editor-fold>

    //<editor-fold desc = "Method">

    /**
     * serialize a check to "tmp/check.serial", this path can't be changer by the user
     * @throws IOException if the file isn't found
     */
    public void serialize() throws IOException {
        FileOutputStream fos = new FileOutputStream("tmp/check.serial");

        ObjectOutputStream oos= new ObjectOutputStream(fos);
        try {
            oos.writeObject(this);
            oos.flush();
            System.out.println("serialized");
        } finally {
            try {
                oos.close();
            } finally {
                fos.close();
            }
        }
    }

    /**
     * deserialize a checks from "tmp/check.serial", this path can't be changed by the user
     * @return Check, the deserialized check
     * @throws Exception if the deserialization failed
     */
    static public Check deserialize() throws Exception {
        Check check = null;
        try {
            FileInputStream fis = new FileInputStream("tmp/check.serial");
            ObjectInputStream ois= new ObjectInputStream(fis);
            try {
                check = (Check) ois.readObject();
            } finally { try {
                ois.close();
            } finally {
                fis.close();
            }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        if(check != null) {
            System.out.println("check deserialize");
            return check;
        }
        throw new Exception("deserialization failed");
    }

    //</editor-fold>


}
