package com.example.bluesky;

import android.os.Bundle;


import com.example.bluesky.ui.dashboard.DashboardFragment;
import com.example.bluesky.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.media.MediaPlayer;

    private static NavController navController=null;
    final private static String url = "http://webinfo.iutmontp.univ-montp2.fr/~chambaudM/BlueSky-JS-Project/";
    private static MediaPlayer lecteur = null;
    private static Song sonEnCours = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppThemeReady);
        super.onCreate(savedInstanceState);
        MainActivity.lecteur = new MediaPlayer();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Button btnPlay = findViewById(R.id.btn1);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        MainActivity.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }


    public static void playSong(final Integer index)
    {
        try
        {
            Song unSon= HomeFragment.getSongList().get(index);
            if(MainActivity.sonEnCours==null||!unSon.getUrl().equals(MainActivity.sonEnCours.getUrl()))
            {

                MainActivity.lecteur.stop();
                MainActivity.lecteur = new MediaPlayer();
                MainActivity.lecteur.setDataSource(unSon.getUrl());
                MainActivity.lecteur.prepare();
                MainActivity.lecteur.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    public void onPrepared(MediaPlayer mp)
                    {
                        MainActivity.lecteur.start();
                        MainActivity.navController.navigate(R.id.navigation_dashboard);
                    }
                });
            }

            MainActivity.sonEnCours = unSon;

            //DashboardFragment.getProgressBar().setMax(MainActivity.getLecteur().getDuration()/1000);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static void pauseSong()
    {
        MainActivity.lecteur.pause();
    }
    public static MediaPlayer getLecteur() {
        return lecteur;
    }

    public static Song getSonEnCours() {
        return sonEnCours;
    }

    public static String getUrl() {
        return url;
    }


}
