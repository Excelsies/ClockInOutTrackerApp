package com.example.clockinouttracker.ui.weeklyview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeeklyFragment extends Fragment implements View.OnClickListener {

    TextView clockIn;
    TextView lunchOut;
    TextView LunchIn;
    TextView clockOut;
    CalendarView calender;

    String day;

    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");

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

                        if(month >= 9)
                            day = (month + 1) + "-" + dayOfMonth + "-" + year;
                        else
                            day = "0" + (month + 1) + "-" + dayOfMonth + "-" + year;

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
        switch (v.getId()) {
            case R.id.clockInTxt:
                Toast.makeText(getContext(), "You clicked the Clock In time!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lunchOutTxt:
                Toast.makeText(getContext(), "You clicked the Lunch Out time!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lunchInTxt:
                Toast.makeText(getContext(), "You clicked the Lunch In time!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clockOutTxt:
                Toast.makeText(getContext(), "You clicked the Clock Out time!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}