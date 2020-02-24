package com.example.bluesky;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity
{

    private static NavController navController = null;
    private static ConstraintLayout CL;
    final private static String url = "http://webinfo.iutmontp.univ-montp2.fr/~chambaudM/BlueSky-JS-Project/";
    private static MediaPlayer lecteur = null;
    private static Song sonEnCours = null;
    private static boolean darkMode;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppThemeReady);
        super.onCreate(savedInstanceState);
        MainActivity.lecteur = new MediaPlayer();
        setContentView(R.layout.activity_main);
        CL = this.findViewById(R.id.seigneur);
        darkMode = true;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        MainActivity.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public static NavController getNavController(){ return navController; }

    public static MediaPlayer getLecteur() {
        return lecteur;
    }

    public static void setLecteur(MediaPlayer unLecteur) {
        lecteur = unLecteur;
    }

    public static Song getSonEnCours() {
        return sonEnCours;
    }

    public static void setSonEncours(Song unSon){ sonEnCours = unSon; }

    public static String getUrl() {
        return url;
    }

    public static ConstraintLayout getCL() { return CL; }

    public static boolean isDarkMode(){ return darkMode; }

    public static void setDarkMode(boolean isDarkMode){ darkMode = isDarkMode; }


}
