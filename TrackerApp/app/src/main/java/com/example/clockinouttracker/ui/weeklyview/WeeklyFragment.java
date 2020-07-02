package com.example.clockinouttracker.ui.weeklyview;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeeklyFragment extends Fragment implements View.OnClickListener {

    TextView clockIn;
    TextView lunchOut;
    TextView LunchIn;
    TextView clockOut;

    CalendarView calender;

    String DayName;
    String day;
    String time;

    View tempView;

    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");
    SimpleDateFormat dayName = new SimpleDateFormat("E");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((GlobalData) getActivity().getApplication()).readFile();
        Date date = new Date();
        day = dayformatter.format(date);
        View root = inflater.inflate(R.layout.fragment_weeklyview, container, false);

        clockIn = root.findViewById(R.id.clockInTxt);
        lunchOut = root.findViewById(R.id.lunchOutTxt);
        LunchIn = root.findViewById(R.id.lunchInTxt);
        clockOut = root.findViewById(R.id.clockOutTxt);

        calender = root.findViewById(R.id.calendarView);

        clockIn.setText(((GlobalData) getActivity().getApplication()).getClockIn(day));
        lunchOut.setText(((GlobalData) getActivity().getApplication()).getLunchOut(day));
        LunchIn.setText(((GlobalData) getActivity().getApplication()).getLunchIn(day));
        clockOut.setText(((GlobalData) getActivity().getApplication()).getClockOut(day));

        clockIn.setOnClickListener(this);
        lunchOut.setOnClickListener(this);
        LunchIn.setOnClickListener(this);
        clockOut.setOnClickListener(this);

        calender.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(
                            @NonNull CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth)
                    {

                        String M = String.valueOf(month + 1);
                        String D = String.valueOf(dayOfMonth);

                        if(month < 9)
                            M = "0" + (month+1);

                        if(dayOfMonth <= 9)
                            D = "0" + dayOfMonth;

                        day = M + "-" + D + "-" + year;

                        try {
                            DayName = dayName.format(dayformatter.parse(day));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        clockIn.setText(((GlobalData) getActivity().getApplication()).getClockIn(day));
                        lunchOut.setText(((GlobalData) getActivity().getApplication()).getLunchOut(day));
                        LunchIn.setText(((GlobalData) getActivity().getApplication()).getLunchIn(day));
                        clockOut.setText(((GlobalData) getActivity().getApplication()).getClockOut(day));
                    }
                });

        return root;
    }



    @Override
    public void onClick(View v) {
        tempView = v;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String M = String.valueOf(selectedMinute);
                String H = String.valueOf(selectedHour);

                if(selectedHour <= 9)
                    H = "0" + selectedHour;

                if(selectedMinute <= 9)
                     M =  "0" + selectedMinute;

                time = H + ":" + M;

                switch (tempView.getId()) {
                    case R.id.clockInTxt:
                        ((GlobalData) getActivity().getApplication()).clockIn(time, day, DayName);
                        clockIn.setText(((GlobalData) getActivity().getApplication()).getClockIn(day));
                        Toast.makeText(getContext(), "Clock In time changed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.lunchOutTxt:
                        ((GlobalData) getActivity().getApplication()).lunchOut(time, day, DayName);
                        lunchOut.setText(((GlobalData) getActivity().getApplication()).getLunchOut(day));
                        Toast.makeText(getContext(), "Lunch Out time changed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.lunchInTxt:
                        ((GlobalData) getActivity().getApplication()).lunchIn(time, day, DayName);
                        LunchIn.setText(((GlobalData) getActivity().getApplication()).getLunchIn(day));
                        Toast.makeText(getContext(), "Lunch In time changed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.clockOutTxt:
                        ((GlobalData) getActivity().getApplication()).clockOut(time, day, DayName);
                        clockOut.setText(((GlobalData) getActivity().getApplication()).getClockOut(day));
                        Toast.makeText(getContext(), "Clock Out time changed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}