package com.example.bluesky;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;
import android.graphics.Color;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ListView songView;
    private JSONArray JSONsong;
    private SongAdapter songAdt=null;
    final private static String url = "http://webinfo.iutmontp.univ-montp2.fr/~chambaudM/BlueSky-JS-Project/song.json";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Button btnPlay = findViewById(R.id.btn1);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        this.songView = (ListView)findViewById(R.id.lesSons);
        this.songList = new ArrayList<Song>();
        this.getJSONSongList();


    }

    public void onClick()
    {

    }



    private void getJSONSongList()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response

                        try
                        {
                            MainActivity.this.JSONsong = new JSONArray(response);
                            MainActivity.this.songList = MainActivity.this.transformJsonSongEnListSong(JSONsong);
                            System.out.println(MainActivity.this.songList);
                            MainActivity.this.songAdt = new SongAdapter(MainActivity.this, MainActivity.this.songList);
                            songView.setAdapter(songAdt);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { System.out.println(error);

            }
        });
        queue.add(stringRequest);

    }
    private ArrayList<Song> transformJsonSongEnListSong(JSONArray json)
    {
        ArrayList<Song> sons = new ArrayList<Song>();
        for (int i = 0; i < json.length(); i++) {

            Song unSon=null;
            try{
                JSONObject jsonobject = json.getJSONObject(i);
                Integer id = jsonobject.getInt("id");
                String name = jsonobject.getString("name");
                String artist = jsonobject.getString("artist");
                String album = jsonobject.getString("album");
                String url = jsonobject.getString("url");
                String coverArtUrl = jsonobject.getString("cover_art_url");
                unSon=new Song(id,name,artist,album,url,coverArtUrl);
            }
            catch (Exception e)
            {

            }
            sons.add(unSon);
        }
        return sons;
    }


}
