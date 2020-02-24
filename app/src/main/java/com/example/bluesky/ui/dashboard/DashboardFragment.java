package com.example.bluesky.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import androidx.core.content.res.ResourcesCompat;
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
import com.example.bluesky.ui.home.HomeFragment;

public class DashboardFragment extends Fragment
{
    private static DashboardFragment instance;

    private TextView titre = null;
    private ImageView cover = null;

    private Button btnPlay;
    private Button btnNext;
    private Button btnPrevious;

    private static Handler mHandler = new Handler();
    private static Utilities utils;

    private static SeekBar progressBar;
    private static TextView songCurrentDurationLabel;
    private static TextView songTotalDurationLabel;
    private static TextView aucunMorceau;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        this.btnPlay = root.findViewById(R.id.btn1);
        this.btnNext = root.findViewById(R.id.btn3);
        this.btnPrevious = (Button) root.findViewById(R.id.btn0);
        this.songCurrentDurationLabel = root.findViewById(R.id.songCurrentDurationLabel);
        this.songTotalDurationLabel = root.findViewById(R.id.songTotalDurationLabel);
        this.aucunMorceau = root.findViewById(R.id.aucunMorceau);
        this.progressBar = root.findViewById(R.id.seekBar);
        
        this.utils = new Utilities();

        btnNext.setVisibility(View.INVISIBLE);
        btnPlay.setVisibility(View.INVISIBLE);
        btnPrevious.setVisibility(View.INVISIBLE);
        songTotalDurationLabel.setVisibility(View.INVISIBLE);
        songCurrentDurationLabel.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
            {

            }


            public void onStartTrackingTouch(SeekBar seekBar)
            {

                mHandler.removeCallbacks(mUpdateTimeTask);
            }


            public void onStopTrackingTouch(SeekBar seekBar)
            {
                mHandler.removeCallbacks(mUpdateTimeTask);

                int totalDuration = MainActivity.getLecteur().getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                MainActivity.getLecteur().seekTo(currentPosition);

                updateProgressBar();


            }
        });

        if(!MainActivity.getLecteur().isPlaying())
        {
            Drawable dr = ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@android:drawable/ic_media_play", null, null), null);

            btnPlay.setBackground(dr);
        }


        this.btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.getLecteur().isPlaying())
                {
                    if(MainActivity.getLecteur() != null)
                    {
                        MainActivity.getLecteur().pause();

                        Drawable dr = ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@android:drawable/ic_media_play", null, null), null);

                        btnPlay.setBackground(dr);
                    }
                }
                else
                {
                    // Resume song
                    if(MainActivity.getLecteur() != null)
                    {
                        MainActivity.getLecteur().start();

                        Drawable dr = ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@android:drawable/ic_media_pause", null, null), null);

                        btnPlay.setBackground(dr);
                    }
                }

            }

        });


        this.btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = MainActivity.getSonEnCours().getId();
                playSong(position);

            }

        });

        this.btnPrevious.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = MainActivity.getSonEnCours().getId() - 2;
                playSong(position);
                setInfosSon();

            }

        });

        MainActivity.getLecteur().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                if(MainActivity.getSonEnCours() != null)
                {
                    int position = MainActivity.getSonEnCours().getId();
                    playSong(position);
                }
            }
        });


        this.titre = root.findViewById(R.id.titre);
        this.cover = root.findViewById(R.id.covertArt);
        this.setInfosSon();
        updateProgressBar();
        return root;
    }

    public void playSong(final Integer index)
    {
        try
        {

            Song unSon = HomeFragment.getSongList().get(index);
            if(MainActivity.getSonEnCours() == null || !unSon.getUrl().equals(MainActivity.getSonEnCours().getUrl()))
            {

                if(MainActivity.getLecteur() != null) MainActivity.getLecteur().stop();
                MainActivity.setLecteur(new MediaPlayer());
                MainActivity.getLecteur().setDataSource(unSon.getUrl());
                MainActivity.getLecteur().prepare();
                MainActivity.getLecteur().setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    public void onPrepared(MediaPlayer mp)
                    {
                        MainActivity.getLecteur().start();
                        MainActivity.getNavController().navigate(R.id.navigation_dashboard);
                    }
                });
            }


            MainActivity.setSonEncours(unSon);

            this.setInfosSon();

            updateProgressBar();



        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void setInfosSon()
    {
        if(aucunMorceau.getVisibility() == View.VISIBLE && MainActivity.getSonEnCours() != null) aucunMorceau.setVisibility(View.INVISIBLE);
        if(btnNext.getVisibility() == View.INVISIBLE && MainActivity.getSonEnCours() != null)
        {
            btnNext.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            songTotalDurationLabel.setVisibility(View.VISIBLE);
            songCurrentDurationLabel.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

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
                            public void onErrorResponse(VolleyError error)
                            {
                                System.out.println(error);
                            }
                        });
                queue.add(imageRequest);
            }
            this.titre.setText(unSon.getArtist()+" - "+unSon.getName());
            this.cover.setImageBitmap(unSon.getImage());

        }
    }

    private  Runnable mUpdateTimeTask = new Runnable()
    {
        public void run()
        {

            if(MainActivity.getLecteur() != null)
            {
                long totalDuration = MainActivity.getLecteur().getDuration();
                long currentDuration = MainActivity.getLecteur().getCurrentPosition();


                songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
                songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

                int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));

                progressBar.setProgress(progress);

                mHandler.postDelayed(this, 100);
            }

        }
    };

    public void updateProgressBar()
    {
        mHandler.postDelayed(mUpdateTimeTask, 100);

    }


    public static DashboardFragment getInstance()
    {
        if(instance == null) instance = new DashboardFragment();
        return instance;
    }


}