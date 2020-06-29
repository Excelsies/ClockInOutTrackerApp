package com.example.clockinouttracker.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    TextView text;

    SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");
    SimpleDateFormat dayName = new SimpleDateFormat("E");

    GlobalData MyData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceStat) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        text = view.findViewById(R.id.text_home);

        Button clockInBtn = view.findViewById(R.id.btnClockIn);
        Button lunchInBtn = view.findViewById(R.id.btnLunchIn);
        Button lunchOutBtn = view.findViewById(R.id.btnLunchOut);
        Button clockOutBtn = view.findViewById(R.id.btnClockOut);

        clockInBtn.setOnClickListener(this);
        lunchInBtn.setOnClickListener(this);
        lunchOutBtn.setOnClickListener(this);
        clockOutBtn.setOnClickListener(this);

        MyData = new GlobalData();

        if(MyData.lastEvent != null){
            text.setText(MyData.lastEvent);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        Date date = new Date();
        String day = dayformatter.format(date);
        String time = timeformatter.format(date);
        String DayName = dayName.format(date);
        switch (v.getId()) {
            case R.id.btnClockIn:
                text.setText("Clocked in for the day at: " + time + " on " + DayName + " " + day);
                MyData.lastEvent = text.getText().toString();
                MyData.clockIn(time, day, DayName);
                Toast.makeText(getContext(), "Clocked In", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLunchIn:
                text.setText("Clocked in from lunch at: " + time + " on " + day);
                MyData.lastEvent = text.getText().toString();
                MyData.lunchIn(time, day, DayName);
                Toast.makeText(getContext(), "Came back from lunch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLunchOut:
                text.setText("Clocked out for lunch at: " + time + " on " + day);
                MyData.lastEvent = text.getText().toString();
                MyData.lunchOut(time, day, DayName);
                Toast.makeText(getContext(), "Leaving for lunch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnClockOut:
                text.setText("Clocked out for the day at: " + time + " on " + day);
                MyData.lastEvent = text.getText().toString();
                MyData.clockOut(time, day, DayName);
                Toast.makeText(getContext(), "Clocked Out", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}