package com.example.bluesky;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluesky.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.text.Layout;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;

import org.json.JSONObject;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import java.io.IOException;
import android.widget.TextView;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {



    final private static String url = "http://webinfo.iutmontp.univ-montp2.fr/~chambaudM/BlueSky-JS-Project/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }



    public static String getUrl() {
        return url;
    }
}
