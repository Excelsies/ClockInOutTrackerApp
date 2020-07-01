package com.example.clockinouttracker.ui.weeklyview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((GlobalData) getActivity().getApplication()).readFile();
        Date date = new Date();
        String day = dayformatter.format(date);
        View root = inflater.inflate(R.layout.fragment_weeklyview, container, false);

        clockIn = root.findViewById(R.id.clockInTxt);
        lunchOut = root.findViewById(R.id.lunchOutTxt);
        LunchIn = root.findViewById(R.id.lunchInTxt);
        clockOut = root.findViewById(R.id.clockOutTxt);

        clockIn.setText(((GlobalData) getActivity().getApplication()).getClockIn(day));
        lunchOut.setText(((GlobalData) getActivity().getApplication()).getLunchOut(day));
        LunchIn.setText(((GlobalData) getActivity().getApplication()).getLunchIn(day));
        clockOut.setText(((GlobalData) getActivity().getApplication()).getClockOut(day));

        return root;
    }
}