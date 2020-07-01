package com.example.clockinouttracker;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GlobalData  extends Application {

    public List<GlobalDates> dates;
    public String lastEvent ;

    private void instantiateLastEvent(){
        lastEvent = null;
    }
    private void instantiateDates(){
        dates = new ArrayList<>();
    }

    public void clockIn(String time, String day, String dayName){
        GlobalDates todaysTimes = new GlobalDates();
        int index = getIndex(day);

        if (index >= 0){
            todaysTimes = dates.get(index);
            todaysTimes.setClockIn(time);
            dates.set(index, todaysTimes);
        }
        else{
            todaysTimes.setDate(day);
            todaysTimes.setDayName(dayName);
            todaysTimes.setClockIn(time);
            dates.add(todaysTimes);
        }
        writeFile();
    }

    public void lunchOut(String time, String day, String dayName){
        GlobalDates todaysTimes = new GlobalDates();
        int index = getIndex(day);

        if (index >= 0){
            todaysTimes = dates.get(index);
            todaysTimes.setLunchOut(time);
            dates.set(index, todaysTimes);
        }
        else{
            todaysTimes.setDate(day);
            todaysTimes.setDayName(dayName);
            todaysTimes.setLunchOut(time);
            dates.add(todaysTimes);
        }

        writeFile();
    }

    public void lunchIn(String time, String day, String dayName){
        GlobalDates todaysTimes = new GlobalDates();
        int index = getIndex(day);

        if (index >= 0){
            todaysTimes = dates.get(index);
            todaysTimes.setLunchIn(time);
            dates.set(index, todaysTimes);
        }
        else{
            todaysTimes.setDate(day);
            todaysTimes.setDayName(dayName);
            todaysTimes.setLunchIn(time);
            dates.add(todaysTimes);
        }

        writeFile();
    }

    public void clockOut(String time, String day, String dayName){
        GlobalDates todaysTimes = new GlobalDates();
        int index = getIndex(day);

        if (index >= 0){
            todaysTimes = dates.get(index);
            todaysTimes.setClockOut(time);
            dates.set(index, todaysTimes);
        }
        else{
            todaysTimes.setDate(day);
            todaysTimes.setDayName(dayName);
            todaysTimes.setClockOut(time);
            dates.add(todaysTimes);
        }

        writeFile();
    }

    public String getClockIn(String day){
        String s = "You have yet to clock in";

        int index = getIndex(day);

        if (index >= 0){
            s = dates.get(index).getClockIn() + " on " + dates.get(index).getDayName() + ", " + dates.get(index).getDate();
            return s;
        }
        return s;
    }

    public String getClockOut(String day){
        String s = "You have yet to clock out";

        int index = getIndex(day);

        if (index >= 0){
            s = dates.get(index).getClockOut() + " on " + dates.get(index).getDayName() + ", " + dates.get(index).getDate();
            return s;
        }
        return s;
    }

    public String getLunchIn(String day){
        String s = "You have yet to arrive from lunch";

        int index = getIndex(day);

        if (index >= 0){
            s = dates.get(index).getLunchIn() + " on " + dates.get(index).getDayName() + ", " + dates.get(index).getDate();
            return s;
        }

        return s;
    }

    public String getLunchOut(String day){
        String s = "You have yet to leave for lunch";

        int index = getIndex(day);

        if (index >= 0){
            s = dates.get(index).getLunchOut() + " on " + dates.get(index).getDayName() + ", " + dates.get(index).getDate();
            return s;
        }
        return s;
    }

    public int getIndex(String date){
        int index = Collections.binarySearch(dates, new GlobalDates(date), new Comparator<GlobalDates>() {

            @Override
            public int compare(GlobalDates o1, GlobalDates o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return index;
    }

    public void clearData(){
        dates.clear();
        lastEvent = null;
        writeFile();
    }

    public void writeFile() {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            final Gson gson = new Gson();

            String json = gson.toJson(dates); //Convert the array to json

            editor.putString("LastEvent", lastEvent);
            editor.putString("TimesList", json); //Put the variable in memory
            editor.apply();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readFile() {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            if(sharedPrefs.contains("LastEvent"))
                lastEvent = sharedPrefs.getString("LastEvent", null);
            else
                instantiateLastEvent();

            if(sharedPrefs.contains("TimesList")) {
                String json = sharedPrefs.getString("TimesList", ""); //Retrieve previously saved data
                dates = (getList(json, GlobalDates.class)); //Restore previous data
            }
            else
                instantiateDates();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

}

class GlobalDates{
    private String myDate = null;
    private String DayName = null;
    private String clockInTime = null;
    private String lunchOutTime = null;
    private String lunchInTime = null;
    private String clockOutTime = null;

    public GlobalDates(){}

    public GlobalDates(String MyDate){
        this.myDate = MyDate;
    }

    public void setDate(String date){
        myDate = date;
    }
    public String getDate(){
        return myDate;
    }

    public void setDayName(String date) { DayName = date; }
    public String getDayName(){
        return DayName;
    }

    public void setClockIn(String time){
        clockInTime = time;
    }
    public String getClockIn(){
        return clockInTime;
    }
    public void setLunchOut(String time){
        lunchOutTime = time;
    }
    public String getLunchOut(){
        return lunchOutTime;
    }
    public void setLunchIn(String time){
        lunchInTime = time;
    }
    public String getLunchIn(){
        return lunchInTime;
    }
    public void setClockOut(String time){
        clockOutTime = time;
    }
    public String getClockOut(){
        return clockOutTime;
    }
}
