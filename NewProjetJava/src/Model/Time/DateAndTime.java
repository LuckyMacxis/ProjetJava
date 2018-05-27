package Model.Time;

import java.io.Serializable;
import java.time.*;

public class DateAndTime implements Serializable{

    private LocalDate date; // can be null
    private LocalTime time;

    /**
     * constant use in constructors TIME => only a time
     */
    public static final int TIME = 1;

    /**
     * constant use in constructors DATE_TIME => a date and a time
     */
    public static final int DATE_TIME = 2;


    //<editor-fold desc = "Constructor">

    /**
     * Constructor with Date and Time
     * @param date LocalDate
     * @param time LocalTime
     * @throws Exception if the attribute time is null
     */
    public DateAndTime(LocalDate date, LocalTime time) throws Exception {
        this.date = date;
        this.time = time;
        this.roundTime();
    }

    /**
     * Constructor without parameters
     */
    public DateAndTime() {
        date = null;
        time = null;
    }

    /**
     * Create and DateAndTime object with a String,
     * the String must be like "dd/mm/yyyy hh:mm"
     * or like "hh:mm" in this case the date is set at null
     * @param str String to convert in a time
     * @param flag = TIME if the String represent only hours and minutes
     *             = DATE_TIME if the String and an hour
     * @throws Exception if the string format is wrong or if the flag != TIME || flag != DATE_TIME
     */
    public DateAndTime(String str, int flag) throws Exception {
        convertStringToDateTime(str, flag);
        this.roundTime();
    }

    public DateAndTime(LocalTime now) throws Exception {
        date = null;
        time = now;
        roundTime();
    }

    //</editor-fold>

    //<editor-fold desc = "Get and Set">

    /**
     * get date
     * @return  the date
     */
    public LocalDate getDate() {
        return date;
    }


    /**
     * set Time
     * @param date -
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }


    /**
     * get Time
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * set the time
     * @param time -
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }


    //</editor-fold>

    //<editor-fold desc = "Method">

    /**
     * Convert a String to a Time, the String has to look like "hh:mm"
     * the must be like "dd/mm/yyyy hh:mm"
     * or like "hh:mm" in this case the date is set at null
     * @param str String
     * @param flag = TIME if the String represent only hours and minutes
     *             = DATE_TIME if the String and an hour
     * @throws Exception if the string format is wrong or if the flag != TIME || flag != DATE_TIME
     */
    private void convertStringToDateTime(String str, int flag) throws Exception {
        int hour,minute, day, month, year;
        String sHour, sMinute, sDay, sMonth, sYear;
        int i = 0;

        if (flag == TIME){
            try {
                sHour = str.split(":")[0];
                sMinute = str.split(":")[1];
            }catch (Exception e){
                throw new Exception("Wrong String to Init, the right form is \"hh:mm\"");
            }

            try {
                sHour = str.split(":")[0];
                sMinute = str.split(":")[1];
            }catch (Exception e){
                throw new Exception("Wrong String to Init, the right form is \"hh:mm\"");
            }

            try {
                hour = Integer.parseInt(sHour);
                minute = Integer.parseInt(sMinute);
            }catch (Exception e){
                throw new Exception("Impossible to convert into an Integer");
            }

            if(hour > 23 || hour < 0 || minute > 59 || minute < 0)
                throw new Exception("minute or hour out of range");

            date = null;
            time = LocalTime.of(hour,minute);
            return;
        }else if (flag == DATE_TIME){
            try {
                sDay = str.split(" ")[0].split("/")[0];
                sMonth = str.split(" ")[0].split("/")[1];
                sYear = str.split(" ")[0].split("/")[2];
                sHour = str.split(" ")[1].split(":")[0];
                sMinute = str.split(" ")[1].split(":")[1];
            }catch (Exception e){
                throw new Exception("String input wrong format for date and time, the right form is \"dd/mm/yyyy hh:mm\"");
            }

            try {
                hour = Integer.parseInt(sHour);
                minute = Integer.parseInt(sMinute);
                day = Integer.parseInt(sDay);
                month = Integer.parseInt(sMonth);
                year = Integer.parseInt(sYear);
            }catch (Exception e){
                throw new Exception("Impossible to convert into an Integer");
            }

            if(hour > 23 || hour < 0 || minute > 59 || minute < 0)
                throw new Exception("minute or hour out of range");

            date = LocalDate.of(year,month,day);
            time = LocalTime.of(hour,minute);
            return;
        }
        throw new Exception("Wrong flag");

    }


    /**
     * round Time to the nearest fifteen minutes
     * @throws Exception if the time is null
     */
    private void roundTime() throws Exception {
        if (time == null)
            throw new Exception("Time is null, can be rounded");
        int minute = time.getMinute();

        if(minute < 8){
            time = time.minusMinutes(minute);
            return ;
        }
        if (minute < 23){
            time = time.minusMinutes(minute);

            time = time.plusMinutes(15);
            return ;
        }
        if (minute < 38){
            time = time.minusMinutes(minute);
            time = time.plusMinutes(30);
            return ;
        }
        if (minute < 53){
            time = time.minusMinutes(minute);
            time = time.plusMinutes(45);
            return ;
        }

        time = time.minusMinutes(minute);
        time = time.plusHours(1);
        return ;
    }

    /**
     * create a clone
     * @return a DateAndTime
     */
    public DateAndTime clone(){
        DateAndTime dateAndTime= new DateAndTime();
        if (time != null)
            dateAndTime.time = LocalTime.of(time.getHour(),time.getMinute());
        if (date != null)
            dateAndTime.date = LocalDate.of(date.getYear(),date.getMonthValue(),date.getDayOfMonth());
        return dateAndTime;
    }

    public String toString(){
        if (date != null && time != null)
            return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" "+time.getHour()+":"+time.getMinute();
        if (time != null && date == null)
            return time.getHour()+":"+time.getMinute();
        return "NaN";
    }
    //</editor-fold>

}
