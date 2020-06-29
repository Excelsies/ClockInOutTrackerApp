package com.example.clockinouttracker.ui.weeklyview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeeklyFragment extends Fragment {

    TextView clockIn;
    TextView lunchOut;
    TextView LunchIn;
    TextView clockOut;

    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");

    GlobalData MyData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Date date = new Date();
        String day = dayformatter.format(date);
        View root = inflater.inflate(R.layout.fragment_weeklyview, container, false);

        clockIn = root.findViewById(R.id.clockInTxt);
        lunchOut = root.findViewById(R.id.lunchOutTxt);
        LunchIn = root.findViewById(R.id.lunchInTxt);
        clockOut = root.findViewById(R.id.clockOutTxt);

        MyData = new GlobalData();

        clockIn.setText(MyData.getClockIn(day));
        lunchOut.setText(MyData.getLunchOut(day));
        LunchIn.setText(MyData.getLunchIn(day));
        clockOut.setText(MyData.getClockOut(day));

        return root;
    }
}