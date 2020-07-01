package com.example.clockinouttracker;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


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
        if(checkCurrentDate(day)){
            todaysTimes = dates.get(dates.size() - 1);
            todaysTimes.setClockIn(time);
            dates.set(dates.size() - 1, todaysTimes);
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
        if(checkCurrentDate(day)){
            todaysTimes = dates.get(dates.size() - 1);
            todaysTimes.setLunchOut(time);
            dates.set(dates.size() - 1, todaysTimes);
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
        if(checkCurrentDate(day)){
            todaysTimes = dates.get(dates.size() - 1);
            todaysTimes.setLunchIn(time);
            dates.set(dates.size() - 1, todaysTimes);
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
        if(checkCurrentDate(day)){
            todaysTimes = dates.get(dates.size() - 1);
            todaysTimes.setClockOut(time);
            dates.set(dates.size() - 1, todaysTimes);
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
        System.out.println("0: " + day);
        if(checkCurrentDate(day)){
            System.out.println("1");
            if(dates.get(dates.size() - 1).getClockIn() != null)
            {
                System.out.println("2");
                s = dates.get(dates.size() - 1).getClockIn();
            }
        }

        return s;
    }

    public String getClockOut(String day){
        String s = "You have yet to clock out";

        if(checkCurrentDate(day)){
            if(dates.get(dates.size() - 1).getClockOut() != null)
            {
                s = dates.get(dates.size() - 1).getClockOut();
            }
        }

        return s;
    }

    public String getLunchIn(String day){
        String s = "You have yet to arrive from lunch";

        if(checkCurrentDate(day)){
            if(dates.get(dates.size() - 1).getLunchIn() != null)
            {
                s = dates.get(dates.size() - 1).getLunchIn();
            }
        }

        return s;
    }

    public String getLunchOut(String day){
        String s = "You have yet to leave for lunch";

        if(checkCurrentDate(day)){
            if(dates.get(dates.size() - 1).getLunchOut() != null)
            {
                s = dates.get(dates.size() - 1).getLunchOut();
            }
        }

        return s;
    }

    public boolean checkCurrentDate(String today){
        if(dates != null) {
            if (dates.size() > 0) {
                if (dates.get(dates.size() - 1).getDate() != today) {
                    return false;
                }
                return true;
            } else
                return false;
        }
        else
            return false;
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
