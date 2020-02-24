package com.example.bluesky;

import android.graphics.Bitmap;

public class Song
{

    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String url;
    private String coverArtUrl;
    private Bitmap image = null;


    public Song(Integer id, String name, String artist, String album, String url, String coverArtUrl)
    {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.url = url;
        this.coverArtUrl = coverArtUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", url='" + url + '\'' +
                ", coverArtUrl='" + coverArtUrl + '\'' +
                '}';
    }

}
