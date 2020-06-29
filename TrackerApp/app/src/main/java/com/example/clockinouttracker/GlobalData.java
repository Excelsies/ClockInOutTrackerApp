package com.example.clockinouttracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GlobalData extends Activity{

    public ArrayList<GlobalDates> dates = new ArrayList<GlobalDates>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<GlobalDates> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(GlobalDates globalDates) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends GlobalDates> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends GlobalDates> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public GlobalDates get(int i) {
            return null;
        }

        @Override
        public GlobalDates set(int i, GlobalDates globalDates) {
            return null;
        }

        @Override
        public void add(int i, GlobalDates globalDates) {

        }

        @Override
        public GlobalDates remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<GlobalDates> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<GlobalDates> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<GlobalDates> subList(int i, int i1) {
            return null;
        }
    };
    public String lastEvent = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        readFile();
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

    private boolean checkCurrentDate(String today){
        if(dates.size() > 0) {
            if (dates.get(dates.size() - 1).getDate() != today) {
                return false;
            }
            return true;
        }
        else
            return false;
    }

    public void writeFile() {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();

            String json = gson.toJson(dates); //Convert the array to json

            editor.putString("TimesList", json); //Put the variable in memory
            editor.putString("LastEvent", lastEvent);
            editor.commit();
        } catch(Exception e){
            e.printStackTrace();
        }
//        try{
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(bos);
//            oos.writeObject(dates);
//            byte[] bytes = bos.toByteArray();
//
//            FileOutputStream fos = openFileOutput ("MyTimes.txt", MODE_PRIVATE);
//            ObjectOutputStream os = new ObjectOutputStream(fos);
//            os.writeObject(bytes);
//            os.close();
//            fos.close();
//        } catch(FileNotFoundException e){
//            e.printStackTrace();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
    }

    public void readFile() {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPrefs.getString("TimesList", null); //Retrieve previously saved data
            lastEvent = sharedPrefs.getString("LastEvent", null);
            if (json != null) {
                Type type = new TypeToken<ArrayList<GlobalDates>>() {
                }.getType();
                dates = gson.fromJson(json, type); //Restore previous data
            }
        } catch(Exception e){
            e.printStackTrace();
        }
//        try {
//            FileInputStream fileInputStream = openFileInput("MyTimes.txt");
//            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
//
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//            int nRead;
//            byte[] data = new byte[16384];
//
//            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//                buffer.write(data, 0, nRead);
//            }
//
//            byte[] buf = buffer.toByteArray();
//
//            ByteArrayInputStream in = new ByteArrayInputStream(buf);
//            ObjectInputStream is = new ObjectInputStream(in);
//            dates = (List<GlobalDates>) is.readObject();
//        } catch (FileNotFoundException | ClassNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }

}

class GlobalDates{
    private String myDate;
    private String DayName;
    private String clockInTime;
    private String lunchOutTime;
    private String lunchInTime;
    private String clockOutTime;

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
