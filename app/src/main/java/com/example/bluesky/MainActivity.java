package com.example.bluesky;

import android.os.Bundle;

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
import android.widget.ListView;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ListView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
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
        JSONArray JSONsong=Async.getJSONSongList();
        this.songList = this.transformJsonSongEnListSong(JSONsong);
        SongAdapter songAdt = new SongAdapter(this, this.songList);
        songView.setAdapter(songAdt);
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
