package com.example.clockinouttracker.ui.shareview;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShareFragment extends Fragment implements View.OnClickListener{

    Boolean timeRangeSelected = false;

    Button btnFrom;
    Button btnTo;
    Button btnSaveToPhone;

    RadioButton rBtnLastTwoWeeks;
    RadioButton rBtnLastWeek;
    RadioButton rBtnMonthToDate;
    RadioButton rBtnCustom;

    final Calendar newCalendar = Calendar.getInstance();
    DatePickerDialog StartTime;
    DatePickerDialog EndTime;
    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((GlobalData) getActivity().getApplication()).readFile();
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        btnFrom = root.findViewById(R.id.fromBtn);
        btnTo = root.findViewById(R.id.toBtn);
        btnSaveToPhone = root.findViewById(R.id.btnSaveToPhone);

        rBtnLastTwoWeeks = root.findViewById(R.id.rBtntwoWeeks);
        rBtnLastWeek = root.findViewById(R.id.rBtnLastWeek);
        rBtnMonthToDate = root.findViewById(R.id.rBtnMonthToDate);
        rBtnCustom = root.findViewById(R.id.rBtnCustom);

        StartTime = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                btnFrom.setText(dayformatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        EndTime = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                btnTo.setText(dayformatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
        btnSaveToPhone.setOnClickListener(this);
        rBtnLastTwoWeeks.setOnClickListener(this);
        rBtnLastWeek.setOnClickListener(this);
        rBtnMonthToDate.setOnClickListener(this);
        rBtnCustom.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rBtntwoWeeks:
                timeRangeSelected = true;
                break;
            case R.id.rBtnLastWeek:
                timeRangeSelected = true;
                break;
            case R.id.rBtnMonthToDate:
                timeRangeSelected = true;
                break;
            case R.id.rBtnCustom:
                timeRangeSelected = true;
                break;
            case R.id.fromBtn:
                StartTime.show();
                Toast.makeText(getContext(), "From Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toBtn:
                EndTime.show();
                Toast.makeText(getContext(), "To Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSaveToPhone:
                if(timeRangeSelected) {
                    ((GlobalData) getActivity().getApplication()).CreateExcel(btnFrom.getText().toString(), btnTo.getText().toString());
                    Toast.makeText(getContext(), "Finished Saving", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Please select time range!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
