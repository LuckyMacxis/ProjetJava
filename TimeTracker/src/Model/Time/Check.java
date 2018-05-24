package Model.Time;

import Model.company.Employee;

import java.io.Serializable;

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


    //</editor-fold>


}
