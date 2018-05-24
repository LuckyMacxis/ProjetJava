package Model.company;

import Model.Time.DateAndTime;

public class Manager extends Employee {

    private boolean chief;


    //<editor-fold desc = "Constructor">

    /**
     * Constructor without mail additionalTime and id and department
     * @param lastname String
     * @param firstname String
     * @param arrivingTime DateAndTime
     * @param departureTime DateAndTime
     * @throws Exception if arguments are null;
     */
    public Manager(String lastname, String firstname, DateAndTime arrivingTime, DateAndTime departureTime) throws Exception {
        super(lastname, firstname, arrivingTime, departureTime);
        chief = false;
    }

    /**
     * Constructor without parameters
     * chief is set at false
     */
    public Manager(){
        super();
        chief = false;
    }

    //</editor-fold>

    //<editor-fold desc = "Get and Set">

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }


    //</editor-fold>

    //<editor-fold desc = "Method">



    //</editor-fold>
}
