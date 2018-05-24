package Model.company;

import Model.Time.Check;
import Model.Time.DateAndTime;

import java.util.ArrayList;

public class Employee extends Person {

    private int id;
    private DateAndTime arrivingTime;
    private DateAndTime departureTime;
    private int additionalTime = 0;
    private ArrayList<Check> listCheck = new ArrayList<>();

    private static int counter = 0;

    //<editor-fold desc = "Constructor"

    /**
     * Constructor without mail and additionalTime and id and department
     * @param lastname String
     * @param firstname String
     * @param arrivingTime DateAndTime
     * @param departureTime DateAndTime
     */
    public Employee(String lastname, String firstname, DateAndTime arrivingTime, DateAndTime departureTime) throws Exception {
        super(lastname, firstname);
        if (arrivingTime == null || departureTime == null)
            throw new Exception("Employee null arguments");
        id = counter;
        counter++;
        this.arrivingTime = arrivingTime;
        this.departureTime = departureTime;
    }

    /**
     * Constructor without parameters
     */
    public Employee() {
        super();
        id = counter;
        counter++;
        arrivingTime = null;
        departureTime = null;
    }

    //</editor-fold>

    //<editor-fold desc = "Get and Set">


    public ArrayList<Check> getListCheck() {
        return listCheck;
    }

    public int getAdditionalTime() {
        return additionalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateAndTime getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(DateAndTime arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public DateAndTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateAndTime departureTime) {
        this.departureTime = departureTime;
    }

    //</editor-fold>

    //<editor-fold desc = "Method">

    /**
     * add a check to an employee
     * this method update the additional time of the employee
     * @param check a Check
     * @throws Exception if the argument is null, or if the check isn't assigned to this employee
     *                   or if the employee have already checked twice today
     *                   or if the check is anterior of the last check of the employee
     */
    public void addCheck(Check check) throws Exception {
        if (check == null)
            throw new Exception("Person null arguments");
        if (this.getId() != check.getEmployee().getId())
            throw new Exception("this check isn't assigned to this employee");

        // verify if the check isn't too old
        if  (listCheck.size() > 0 && check.getCheck().getDate().isBefore(listCheck.get(listCheck.size()-1).getCheck().getDate())) {
            throw new Exception("this check is too old");
        }else{
            if (listCheck.size() > 0 && check.getCheck().getDate().equals(listCheck.get(listCheck.size()-1).getCheck().getDate())){
                if (check.getCheck().getTime().isBefore(listCheck.get(listCheck.size()-1).getCheck().getTime()))
                    throw new Exception("this check is too old");
            }
        }

        // we look if the employee have already check today
        // if yes it's mean that it's a departure time
        if (listCheck.size() > 0 && listCheck.get(listCheck.size()-1).getCheck().getDate().equals(check.getCheck().getDate())){

            // we look if the employee have already check twice today
            // if yes we throw an Exception
            if (listCheck.size() >= 2){
                if (listCheck.get(listCheck.size()-2).getCheck().getDate().equals(check.getCheck().getDate()))
                    throw new Exception("this employee have already check twice today");
            }
            check.setDepartureTime(this.departureTime);
            additionalTime += (check.getCheck().getTime().getHour()*60 + check.getCheck().getTime().getMinute())
                    - (departureTime.getTime().getHour()*60 + departureTime.getTime().getMinute());
        }else{
            check.setArrivingTime(this.arrivingTime);
            additionalTime += (arrivingTime.getTime().getHour()*60 + arrivingTime.getTime().getMinute())
                    - (check.getCheck().getTime().getHour()*60 + check.getCheck().getTime().getMinute());
        }
        listCheck.add(check);
    }

    //</editor-fold>

}
