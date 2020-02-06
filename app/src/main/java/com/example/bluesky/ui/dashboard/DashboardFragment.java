package com.example.bluesky.ui.dashboard;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluesky.MainActivity;
import com.example.bluesky.R;
import com.example.bluesky.Song;
import com.example.bluesky.ui.home.HomeFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView titre = null;
    private ImageView cover=null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        this.titre=root.findViewById(R.id.titre);
        this.cover=root.findViewById(R.id.covertArt);
        this.setInfosSon();
        return root;
    }

    public void setInfosSon()
    {
        Song unSon= MainActivity.getSonEnCours();
        if(unSon!=null)
        {
            if(unSon.getImage()==null)
            {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                ImageRequest imageRequest = new ImageRequest( unSon.getCoverArtUrl(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                MainActivity.getSonEnCours().setImage(bitmap);
                                //HomeFragment.getSongList().set(index,DashboardFragment.sonEnCours);
                                DashboardFragment.this.setInfosSon();
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP,null,
                        new Response.ErrorListener() {
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




}