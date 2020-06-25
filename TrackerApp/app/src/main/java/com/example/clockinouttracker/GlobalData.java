package com.example.clockinouttracker;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GlobalData {

    private LocalDate myDate;
    private Time clockInTime;
    private Time lunchOutTime;
    private Time lunchInTime;
    private Time clockOutTime;

    public void setDate(LocalDate date){
        myDate = date;
    }

    public LocalDate getDate(){
        return myDate;
    }

    public void setClockIn(Time time){
        clockInTime = time;
    }
    public Time getClockIn(){
        return clockInTime;
    }
    public void setLunchOut(Time time){
        lunchOutTime = time;
    }
    public Time getLunchOut(){
        return lunchOutTime;
    }
    public void setLunchIn(Time time){
        lunchInTime = time;
    }
    public Time getLunchIn(){
        return lunchInTime;
    }
    public void setClockOut(Time time){
        clockOutTime = time;
    }
    public Time getClockOut(){
        return clockOutTime;
    }

}
