package com.example.clockinouttracker.ui.shareview;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clockinouttracker.GlobalData;
import com.example.clockinouttracker.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShareFragment extends Fragment implements View.OnClickListener{

    Boolean timeRangeSelected = false;

    Button btnFrom;
    Button btnTo;
    Button btnSaveToPhone;
    Button btnEmail;

    RadioButton rBtnLastTwoWeeks;
    RadioButton rBtnLastWeek;
    RadioButton rBtnMonthToDate;
    RadioButton rBtnCustom;
    RadioGroup rGroup;

    final Calendar newCalendar = Calendar.getInstance();
    Calendar todayDate;
    DatePickerDialog StartTime;
    DatePickerDialog EndTime;
    SimpleDateFormat dayformatter = new SimpleDateFormat("MM-dd-yyyy");

    String startDate;
    String EndDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((GlobalData) getActivity().getApplication()).readFile();
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        todayDate = Calendar.getInstance();

        btnFrom = root.findViewById(R.id.fromBtn);
        btnTo = root.findViewById(R.id.toBtn);
        btnSaveToPhone = root.findViewById(R.id.btnSaveToPhone);
        btnEmail = root.findViewById(R.id.btnEmail);

        rBtnLastTwoWeeks = root.findViewById(R.id.rBtntwoWeeks);
        rBtnLastWeek = root.findViewById(R.id.rBtnLastWeek);
        rBtnMonthToDate = root.findViewById(R.id.rBtnMonthToDate);
        rBtnCustom = root.findViewById(R.id.rBtnCustom);
        rGroup = root.findViewById(R.id.radioGroup);

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
        btnEmail.setOnClickListener(this);

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
                todayDate = Calendar.getInstance();
                timeRangeSelected = true;
                break;
            case R.id.rBtnLastWeek:
                todayDate = Calendar.getInstance();
                timeRangeSelected = true;
                break;
            case R.id.rBtnMonthToDate:
                todayDate = Calendar.getInstance();
                timeRangeSelected = true;
                break;
            case R.id.rBtnCustom:
                todayDate = Calendar.getInstance();
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
                    saveExcel();
                }
                else
                    Toast.makeText(getContext(), "Please select time range!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnEmail:
                if(timeRangeSelected) {
                    saveExcel();

                    String Fnamexls="MyHours " + startDate + " to " + EndDate + ".xls";
                    File directory = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/TimeTracker");
                    File file = new File(directory, Fnamexls);
                    Uri path = Uri.fromFile(file);

                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_STREAM, path);

                    try { startActivity(intent);}
                    catch(Exception e){
                        Toast.makeText(getContext(), "Could not open your Email app.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getContext(), "Please select time range!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void saveExcel(){
        EndDate = dayformatter.format(todayDate.getTime());
        switch(rGroup.getCheckedRadioButtonId()){
            case R.id.rBtntwoWeeks:
                todayDate.add(Calendar.DAY_OF_YEAR, -14);
                startDate = dayformatter.format(todayDate.getTime());
                break;
            case R.id.rBtnLastWeek:
                todayDate.add(Calendar.DAY_OF_YEAR, -7);
                startDate = dayformatter.format(todayDate.getTime());
                break;
            case R.id.rBtnMonthToDate:
                todayDate.set(Calendar.DAY_OF_MONTH, 1);
                startDate = dayformatter.format(todayDate.getTime());
                break;
            case R.id.rBtnCustom:
                startDate = btnFrom.getText().toString();
                EndDate = btnTo.getText().toString();
                break;
            default:
                startDate = EndDate;
        }

        ((GlobalData) getActivity().getApplication()).CreateExcel(startDate, EndDate);
        Toast.makeText(getContext(), "Finished Saving", Toast.LENGTH_SHORT).show();
    }


}
