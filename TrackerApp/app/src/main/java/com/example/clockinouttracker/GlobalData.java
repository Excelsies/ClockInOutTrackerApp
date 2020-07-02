package com.example.clockinouttracker;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


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

    public void CreateExcel(String fromDate, String toDate) {

        SimpleDateFormat myFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(myFormat.parse(fromDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int dayCount = dayAmount(fromDate, toDate);

        try {
            WritableWorkbook workbook = createWorkbook();
            int a = 1;
            WritableSheet sheet = workbook.createSheet("My Times", 0);
            try {
                writeCell(1, 0, "Day Name", true, sheet);
                writeCell(2, 0, "Clock In", true, sheet);
                writeCell(3, 0, "Lunch Out", true, sheet);
                writeCell(4, 0, "Lunch In", true, sheet);
                writeCell(5, 0, "Clock Out", true, sheet);
                writeCell(6, 0, "Total Time", true, sheet);

                for(int i = 0; i < dayCount + 1; i++){
                    String s = myFormat.format(c.getTime());
                    writeCell(0, i+1, s, true, sheet);
                    int index = getIndex(s);
                    if (index >= 0){
                        writeCell(1, i+1, dates.get(index).getDayName(), false, sheet);
                        writeCell(2, i+1, dates.get(index).getClockIn(), false, sheet);
                        writeCell(3, i+1, dates.get(index).getLunchOut(), false, sheet);
                        writeCell(4, i+1, dates.get(index).getLunchIn(), false, sheet);
                        writeCell(5, i+1, dates.get(index).getClockOut(), false, sheet);
                    }

                    c.add(Calendar.DATE, 1);
                }

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            workbook.write();

            try {
                workbook.close();
            } catch (WriteException e) {

                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int dayAmount(String FromDay, String ToDay){
        SimpleDateFormat myFormat = new SimpleDateFormat("MM-dd-yyyy");

        int amount = 0;

        try {
            Date date1 = myFormat.parse(FromDay);
            Date date2 = myFormat.parse(ToDay);
            long diff = date2.getTime() - date1.getTime();
            amount = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return amount;
    }

    private WritableWorkbook createWorkbook() throws IOException {
        String Fnamexls="MyTimes"  + ".xls";

        File directory = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/TimeTracker");
        directory.mkdirs();
        System.out.println(directory);

        File file = new File(directory, Fnamexls);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        wbSettings.setUseTemporaryFileDuringWrite(true);


        return Workbook.createWorkbook(file, wbSettings);
    }

    public void writeCell(int columnPosition, int rowPosition, String contents, boolean headerCell,
                          WritableSheet sheet) throws RowsExceededException, WriteException{
        //create a new cell with contents at position
        Label newCell = new Label(columnPosition,rowPosition,contents);

        if (headerCell){
            //give header cells size 10 Arial bolded
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            //center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
        }

        sheet.addCell(newCell);
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
