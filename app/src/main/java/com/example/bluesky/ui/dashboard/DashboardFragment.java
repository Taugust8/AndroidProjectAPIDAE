package com.example.bluesky.ui.dashboard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluesky.MainActivity;
import com.example.bluesky.R;
import com.example.bluesky.Song;
import com.example.bluesky.Utilities;

public class DashboardFragment extends Fragment
{

    private DashboardViewModel dashboardViewModel;
    private TextView titre = null;
    private ImageView cover = null;

    private Button btnPlay;
    private Button btnPause;
    private Button btnNext;
    private Button btnPrevious;

    //private Handler mHandler = new Handler();
    //private Utilities utils;

    //private static SeekBar progressBar;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        this.btnPlay = (Button) root.findViewById(R.id.btn1);
        this.btnPause = (Button) root.findViewById(R.id.btn2);
        this.btnNext = (Button) root.findViewById(R.id.btn3);
        this.btnPrevious = (Button) root.findViewById(R.id.btn0);
        this.songCurrentDurationLabel = root.findViewById(R.id.songTotalDurationLabel);
        this.songTotalDurationLabel = root.findViewById(R.id.songTotalDurationLabel);
        //this.progressBar = root.findViewById(R.id.seekBar);


        //this.utils = new Utilities();


        btnPause.setVisibility(View.INVISIBLE);
        //btnPlay.setVisibility(View.INVISIBLE);
        //btnNext.setVisibility(View.INVISIBLE);
        //btnPrevious.setVisibility(View.INVISIBLE);


        this.btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.getLecteur().start();
                btnPlay.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }

        });

        this.btnPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.getLecteur().pause();
                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
            }

        });

        this.btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = MainActivity.getSonEnCours().getId();
                MainActivity.playSong(position);
                setInfosSon();
            }

        });

        this.btnPrevious.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.playSong(MainActivity.getSonEnCours().getId() - 2);
                setInfosSon();
            }

        });


        this.titre = root.findViewById(R.id.titre);
        this.cover = root.findViewById(R.id.covertArt);
        this.setInfosSon();
        return root;
    }

    public void setInfosSon()
    {
        btnPlay.setVisibility(View.INVISIBLE);
        btnPause.setVisibility(View.VISIBLE);
        Song unSon= MainActivity.getSonEnCours();
        if(unSon != null)
        {
            if(unSon.getImage() == null)
            {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                ImageRequest imageRequest = new ImageRequest( unSon.getCoverArtUrl(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                MainActivity.getSonEnCours().setImage(bitmap);
                                DashboardFragment.this.setInfosSon();
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP,null,
                        new Response.ErrorListener()
                        {
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error);
                            }
                        });
                queue.add(imageRequest);
            }
            this.titre.setText(unSon.getArtist()+" - "+unSon.getName());
            this.cover.setImageBitmap(unSon.getImage());
        }
    }

/*

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = MainActivity.getLecteur().getDuration();
            long currentDuration = MainActivity.getLecteur().getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            progressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }



    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }


    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = MainActivity.getLecteur().getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        MainActivity.getLecteur().seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    public static SeekBar getProgressBar()
    {
        return progressBar;
    }*/

}