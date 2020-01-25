package com.example.bluesky;

public class Song {

    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String url;
    private String coverArtUrl;

    public Song() {
    }

    public Song(Integer id, String name, String artist, String album, String url, String coverArtUrl) {
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

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public void setCoverArtUrl(String coverArtUrl) {
        this.coverArtUrl = coverArtUrl;
    }

}
