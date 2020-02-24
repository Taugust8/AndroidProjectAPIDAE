package com.example.bluesky.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.CompoundButton;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bluesky.MainActivity;
import com.example.bluesky.R;

public class NotificationsFragment extends Fragment
{

    private Switch btnSwitch;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        this.btnSwitch = root.findViewById(R.id.switch1);

        if(MainActivity.isDarkMode()) this.btnSwitch.setChecked(true);

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!isChecked)
                {
                    MainActivity.getCL().setBackgroundColor(Color.GRAY);
                    MainActivity.setDarkMode(false);
                }
                else
                {
                    MainActivity.getCL().setBackgroundColor(Color.parseColor("#333333"));
                    MainActivity.setDarkMode(true);
                }
            }
        });

        return root;
    }
}