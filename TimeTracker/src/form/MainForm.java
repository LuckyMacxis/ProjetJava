package form;

import Model.Time.DateAndTime;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.time.*;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainForm extends JFrame {
    private JPanel panel1;
    private JButton checkInOutButton;
    private JLabel timeLabel;
    private JLabel roundedTimeLabel;
    private JButton optionButton;
    private JButton syncButton;
    private JComboBox comboBoxEmployee;

    public JComboBox getComboBoxEmployee() {
        return comboBoxEmployee;
    }

    LocalDateTime dateTime;

    public MainForm(){
        setContentPane(panel1);
        setTitle("Time Tracker Emulator");
        setSize(400,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                dateTime = LocalDateTime.of(LocalDate.now(),LocalTime.now());
                timeLabel.setText(String.valueOf("<html><FONT size=\"5\">"+dateTime.getDayOfMonth()+"/"+dateTime.getMonthValue()+"/"+dateTime.getYear()+"</FONT size=\"5\"><br></br><FONT size=\"6\">"
                +dateTime.getHour()+":"+dateTime.getMinute()+":"+dateTime.getSecond())+"</FONT size=\"6\"></html>");
                try {
                    roundedTimeLabel.setText(String.valueOf("Let's say "+new DateAndTime(LocalTime.now())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    public void buttonCheckListener(ActionListener actionListener){
        checkInOutButton.addActionListener(actionListener);
    }

    public void buttonOptionListener(ActionListener actionListener){
        optionButton.addActionListener(actionListener);
    }

    public void buttonSyncListener(ActionListener actionListener){
        syncButton.addActionListener(actionListener);
    }

}
