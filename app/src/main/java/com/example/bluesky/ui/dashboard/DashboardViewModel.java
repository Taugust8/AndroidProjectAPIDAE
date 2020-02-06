package com.example.bluesky.ui.dashboard;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bluesky.MainActivity;
import com.example.bluesky.R;
import com.example.bluesky.Song;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}