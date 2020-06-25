package com.example.clockinouttracker.ui.weeklyview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeeklyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WeeklyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the weekly fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
