package com.example.bluesky.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluesky.MainActivity;
import com.example.bluesky.R;
import com.example.bluesky.Song;
import com.example.bluesky.SongAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{

    private ListView songView;
    private static ArrayList<Song> songList;
    private JSONArray JSONsong;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
         this.songView = root.findViewById(R.id.lesSons);
        HomeFragment.songList = new ArrayList<Song>();


        this.getJSONSongList();


        return root;


    }


    private void getJSONSongList()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.getUrl()+"song.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            HomeFragment.this.JSONsong = new JSONArray(response);
                            HomeFragment.songList = HomeFragment.this.transformJsonSongEnListSong(JSONsong);
                            SongAdapter unAdapter=new SongAdapter(getContext(),HomeFragment.songList);
                            HomeFragment.this.songView.setAdapter(unAdapter);
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
        for (int i = 0; i < json.length(); i++)
        {

            Song unSon=null;
            try
            {
                JSONObject jsonobject = json.getJSONObject(i);
                Integer id = jsonobject.getInt("id");
                String name = jsonobject.getString("name");
                String artist = jsonobject.getString("artist");
                String album = jsonobject.getString("album");
                String url = MainActivity.getUrl()+jsonobject.getString("url");
                String coverArtUrl =MainActivity.getUrl()+ jsonobject.getString("cover_art_url");
                unSon=new Song(id,name,artist,album,url,coverArtUrl);
                sons.add(unSon);
            }
            catch (Exception e)
            {

            }
        }
        return sons;
    }

    public static ArrayList<Song> getSongList() {
        return songList;
    }
}